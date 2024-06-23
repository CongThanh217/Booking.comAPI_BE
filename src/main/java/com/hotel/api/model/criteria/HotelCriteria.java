package com.hotel.api.model.criteria;

import com.hotel.api.model.Hotel;
import com.hotel.api.model.Nation;
import com.hotel.api.model.User;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class HotelCriteria {
    private Long id;
    private String name;
    private String userId;
    private Integer stars;
    private Integer status;
    private String province;
    private String district;
    private String ward;
    private Long provinceId;
    private Long districtId;
    private Long wardId;

    public Specification<Hotel> getSpecification() {
        return new Specification<Hotel>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!StringUtils.isBlank(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName() + "%"));
                }
                if (getUserId() != null) {
                    Join<Hotel, User> join = root.join("user", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getUserId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                if (getStars() != null) {
                    predicates.add(cb.equal(root.get("stars"), getStars()));
                }
                if (!StringUtils.isBlank(getProvince())) {
                    Join<Hotel, Nation> join = root.join("province", JoinType.INNER);
                    predicates.add(cb.like(cb.lower(join.get("name")), "%" + getProvince() + "%"));
                }
                if (!StringUtils.isBlank(getDistrict())) {
                    Join<Hotel, Nation> join = root.join("district", JoinType.INNER);
                    predicates.add(cb.like(cb.lower(join.get("name")), "%" + getDistrict() + "%"));
                }
                if (!StringUtils.isBlank(getWard())) {
                    Join<Hotel, Nation> join = root.join("ward", JoinType.INNER);
                    predicates.add(cb.like(cb.lower(join.get("name")), "%" + getWard() + "%"));
                }
                if (getProvinceId() != null) {
                    Join<Hotel, Nation> join = root.join("province", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getProvinceId()));
                }
                if (getDistrictId() != null) {
                    Join<Hotel, Nation> join = root.join("district", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getDistrictId()));
                }
                if (getWardId() != null) {
                    Join<Hotel, Nation> join = root.join("ward", JoinType.INNER);
                    predicates.add(cb.equal(join.get("id"), getWardId()));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

}
