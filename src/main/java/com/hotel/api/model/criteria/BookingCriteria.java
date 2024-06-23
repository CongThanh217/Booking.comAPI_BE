package com.hotel.api.model.criteria;

import com.hotel.api.model.Booking;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookingCriteria {

    private Long id;
    private Integer price;
    private Integer paymentMethod;
    private Boolean paymentStatus;
    private Integer gender;
    private String bookingCode;
    private String phone;
    private String name;
    private String email ;
    private Date checkIn;
    private Date startDate;
    private Date endDate;
    private Integer status;
    private Long hotelId;
    public Specification<Booking> getSpecification() {
        return new Specification<Booking>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                if (getHotelId() != null) {
                    predicates.add(cb.equal(root.get("hotel").get("id"), getHotelId()));
                }
                if (!StringUtils.isBlank(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName() + "%"));
                }
                if (getPaymentMethod() != null) {
                    predicates.add(cb.equal(root.get("paymentMethod"), getPaymentMethod()));
                }
                if (getPaymentStatus() != null) {
                    predicates.add(cb.equal(root.get("paymentStatus"), getPaymentStatus()));
                }
                if (!StringUtils.isEmpty(getBookingCode())) {
                    predicates.add(cb.equal(
                            cb.function("binary", String.class, root.get("orderCode")),
                            cb.literal(getBookingCode())
                    ));
                }
                if (getPhone() != null) {
                    predicates.add(cb.equal(root.get("phone"), getPhone()));
                }
                if (getCheckIn() != null) {
                    Expression<Date> createDateExpression = root.get("checkIn");
                    Expression<Date> createDateToCompare = cb.literal(getCheckIn());

                    // Lấy chỉ ngày, tháng và năm từ createDateExpression và createDateToCompare
                    Expression<Date> createDateExpressionDateOnly = cb.function(
                            "DATE",
                            Date.class,
                            createDateExpression
                    );
                    Expression<Date> createDateToCompareDateOnly = cb.function(
                            "DATE",
                            Date.class,
                            createDateToCompare
                    );
                    predicates.add(cb.equal(createDateExpressionDateOnly, createDateToCompareDateOnly));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
