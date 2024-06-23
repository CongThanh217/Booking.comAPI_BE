package com.hotel.api.controller;

import com.hotel.api.constant.UserBaseConstant;
import com.hotel.api.dto.ApiMessageDto;
import com.hotel.api.dto.ApiResponse;
import com.hotel.api.dto.ErrorCode;
import com.hotel.api.dto.ResponseListDto;
import com.hotel.api.dto.account.ForgetPasswordDto;
import com.hotel.api.dto.user.UserAutoCompleteDto;
import com.hotel.api.dto.user.UserDto;
import com.hotel.api.form.user.*;
import com.hotel.api.mapper.AccountMapper;
import com.hotel.api.mapper.UserMapper;
import com.hotel.api.model.Account;
import com.hotel.api.model.Group;
import com.hotel.api.model.User;
import com.hotel.api.model.criteria.UserCriteria;
import com.hotel.api.repository.*;
import com.hotel.api.service.EmailService;
import com.hotel.api.service.UserBaseApiService;
import com.hotel.api.utils.AESUtils;
import com.hotel.api.utils.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class UserController extends ABasicController{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserBaseApiService userBaseApiService;
    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/signup", produces= MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ForgetPasswordDto> create(@Valid @RequestBody SignUpUserForm signUpUserForm, BindingResult bindingResult) throws MessagingException {
        ApiMessageDto<ForgetPasswordDto> apiMessageDto = new ApiMessageDto<>();
        Account accountByUsername = accountRepository.findAccountByUsername(signUpUserForm.getUserName());
        if (accountByUsername!=null)
        {
            apiMessageDto.setMessage("username already exists");
            apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (signUpUserForm.getEmail()!=null)
        {
            Account accountByEmail = accountRepository.findAccountByEmail(signUpUserForm.getEmail());
            if (accountByEmail!=null)
            {
                apiMessageDto.setMessage("email already exists");
                apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
        }
        Account account = accountMapper.fromSignUpUserToAccount(signUpUserForm);
        account.setPassword(passwordEncoder.encode(signUpUserForm.getPassword()));
        account.setKind(signUpUserForm.getKind());
        Group group = groupRepository.findFirstByKind(signUpUserForm.getKind());
        account.setGroup(group);
        account.setStatus(UserBaseConstant.STATUS_PENDING);
        accountRepository.save(account);

        User user = new User();
        user.setAccount(account);
        user.setPoint(0);
        user.setStatus(UserBaseConstant.STATUS_LOCK);
        userRepository.save(user);

        String otp = userBaseApiService.getOTPForgetPassword();
        account.setAttemptCode(0);
        account.setResetPwdCode(otp);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);

        //send email
//        userBaseApiService.sendEmail(account.getEmail(),"OTP: "+otp, "confirm",false);
        emailService.sendOtpToEmail(account.getEmail(),otp);

        ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
        String hash = AESUtils.encrypt (account.getId()+";"+otp, true);
        forgetPasswordDto.setIdHash(hash);

        apiMessageDto.setData(forgetPasswordDto);
        apiMessageDto.setMessage("Check your email to receive otp to complete the final registration step");
        return apiMessageDto;
    }
    @GetMapping(value = "/get/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_V')")
    public ApiMessageDto<UserDto> getUser(@PathVariable("id") Long id)
    {
        ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
        User user = userRepository.findById(id).orElse(null);
        if (user==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not found user");
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(userMapper.fromEntityToUserDto(user));
        apiMessageDto.setMessage("get user success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('US_D')")
    public ApiMessageDto<String> deleteUser(@PathVariable("id") Long id)
    {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        User user = userRepository.findById(id).orElse(null);
        if (user==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not found user");
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Account account = accountRepository.findById(user.getAccount().getId()).orElse(null);
        if (account.getIsSuperAdmin()){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not allow delete super admin");
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_ALLOW_DELETE_SUPPER_ADMIN);
            return apiMessageDto;
        }
        addressRepository.deleteAllByUserId(id);
        userRepository.delete(user);
        accountRepository.delete(account);
        apiMessageDto.setMessage("Delete User success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces= MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_L')")
    public ApiMessageDto<ResponseListDto<List<UserDto>>> getList(UserCriteria userCriteria , Pageable pageable)
    {

        ApiMessageDto<ResponseListDto<List<UserDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<UserDto>> responseListDto = new ResponseListDto<>();
        Page<User> listUser = userRepository.findAll(userCriteria.getSpecification(),pageable);
        responseListDto.setContent(userMapper.fromUserListToUserDtoList(listUser.getContent()));
        responseListDto.setTotalPages(listUser.getTotalPages());
        responseListDto.setTotalElements(listUser.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list user success");
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<List<UserAutoCompleteDto>>> ListAutoComplete(UserCriteria userCriteria)
    {
        ApiMessageDto<ResponseListDto<List<UserAutoCompleteDto>>> apiMessageDto = new ApiMessageDto<>();
        ResponseListDto<List<UserAutoCompleteDto>> responseListDto = new ResponseListDto<>();
        Pageable pageable = PageRequest.of(0,10);
        Page<User> listUser =userRepository.findAll(userCriteria.getSpecification(),pageable);
        responseListDto.setContent(userMapper.fromUserListToUserDtoListAutocomplete(listUser.getContent()));
        responseListDto.setTotalPages(listUser.getTotalPages());
        responseListDto.setTotalElements(listUser.getTotalElements());

        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("get success");
        return apiMessageDto;
    }
    @PostMapping(value = "/confirm_otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> forgetPassword(@Valid @RequestBody ConfirmOtp confirmOtp, BindingResult bindingResult){
        ApiResponse<String> apiMessageDto = new ApiResponse<>();

        String[] hash = AESUtils.decrypt(confirmOtp.getIdHash(),true).split(";",2);
        Long id = ConvertUtils.convertStringToLong(hash[0]);
        if(id <= 0){
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_WRONG_HASH_RESET_PASS);
            return apiMessageDto;
        }

        Account account = accountRepository.findById(id).orElse(null);
        if (account == null ) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Không tìm thấy người dùng");
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }

        if(account.getAttemptCode() >= UserBaseConstant.MAX_ATTEMPT_FORGET_PWD){
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Chỉ được nhập tối đa 5 lần vui lòng bấm gửi lại OTP");
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_LOCKED);
            return apiMessageDto;
        }

        if(!account.getResetPwdCode().equals(confirmOtp.getOtp()) ||
                (new Date().getTime() - account.getResetPwdTime().getTime() >= UserBaseConstant.MAX_TIME_FORGET_PWD)){

            //tang so lan
            account.setAttemptCode(account.getAttemptCode()+1);
            accountRepository.save(account);

            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("OTP đã hết hiệu lực vui lòng bấm gửi lại OTP");
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_OPT_INVALID);
            return apiMessageDto;
        }

        account.setResetPwdTime(null);
        account.setResetPwdCode(null);
        account.setAttemptCode(null);
        account.setStatus(UserBaseConstant.STATUS_ACTIVE);
        accountRepository.save(account);

        User user = userRepository.findByAccountId(account.getId()).orElse(null);
        user.setStatus(UserBaseConstant.STATUS_ACTIVE);
        userRepository.save(user);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("sign up success.");
        return  apiMessageDto;
    }
    @Transactional
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('US_U')")
    public ApiMessageDto<String> update(@Valid @RequestBody UpdateUserForm updateUserForm, BindingResult bindingResult) {

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        User user = userRepository.findById(updateUserForm.getId()).orElse(null);
        if (user==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not found user");
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (!user.getAccount().getEmail().equalsIgnoreCase(updateUserForm.getEmail()))
        {
            Account account = accountRepository.findAccountByEmail(updateUserForm.getEmail());
            if(account!=null)
            {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.USER_ERROR_EXIST);
                apiMessageDto.setMessage("Email is existed");
                return apiMessageDto;
            }

        }
        Account account = accountRepository.findById(user.getAccount().getId()).orElse(null);
        if(StringUtils.isNoneBlank(updateUserForm.getPassword()))
        {
            account.setPassword(passwordEncoder.encode(updateUserForm.getPassword()));
        }
        accountMapper.fromUpdateUserFormToEntity(updateUserForm,account);
        accountRepository.save(account);
        apiMessageDto.setMessage("update success");
        return apiMessageDto;
    }
    @PutMapping(value = "/update-profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfile(@Valid @RequestBody UpdateMyprofile updateMyprofile , BindingResult bindingResult)
    {
        // get account id
        Long accountId = getCurrentUser();

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Not found this account");
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        User user = userRepository.findByAccountId(accountId).orElse(null);
        if (!account.getEmail().equalsIgnoreCase(updateMyprofile.getEmail()))
        {
            Account emailExist = accountRepository.findAccountByEmail(updateMyprofile.getEmail());
            if (emailExist!=null)
            {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Email already exist");
                apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_EMAIL_EXIST);
                return apiMessageDto;
            }
        }
        if(updateMyprofile.getOldPassword()!=null)
        {
            if (!passwordEncoder.matches(updateMyprofile.getOldPassword(),account.getPassword()))
            {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("password is incorrect");
                apiMessageDto.setCode(ErrorCode.USER_ERROR_WRONG_PASSWORD);
                return apiMessageDto;
            }
            if (StringUtils.isNoneBlank(updateMyprofile.getNewPassword()))
            {
                account.setPassword(passwordEncoder.encode(updateMyprofile.getNewPassword()));
            }
        }

        if (updateMyprofile.getBirthday()!=null)
        {
            user.setBirthday(updateMyprofile.getBirthday());
        }
        if (updateMyprofile.getGender()!=null)
        {
            user.setGender(updateMyprofile.getGender());
        }
        accountMapper.fromUpdateMyProfileToEntity(updateMyprofile,account);
        accountRepository.save(account);
        userRepository.save(user);
        apiMessageDto.setMessage("update myprofile success");
        return apiMessageDto;
    }
    @GetMapping(value = "/get-myprofile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UserDto> getMyProfile()
    {
        ApiMessageDto<UserDto> apiMessageDto = new ApiMessageDto<>();
        Long accountId = getCurrentUser();

        Account account = accountRepository.findById(accountId).orElse(null);
        if (account==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("account not found");
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        User user = userRepository.findByAccountId(accountId).orElse(null);
        if (user==null)
        {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("user not found");
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(userMapper.fromEntityToUserDto(user));
        apiMessageDto.setMessage("get profile success");
        return apiMessageDto;
    }




}
