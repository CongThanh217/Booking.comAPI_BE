package com.hotel.api.model.criteria;

import com.hotel.api.model.EmptyRoom;
import com.hotel.api.model.Hotel;
import com.hotel.api.model.KindOfRoom;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class KindOfRoomCriteria {

    private Integer priceStart;
    private Integer priceEnd;
    private Integer status;
    private Long hotelId;
    private Long provinceId;
    private Long wardId;
    private Long districtId;
    private Integer emptyRoom;

    public Specification<KindOfRoom> getSpecification() {
        return new Specification<KindOfRoom>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<KindOfRoom> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                // Create a subquery for EmptyRoom
                Subquery<Integer> subquery = query.subquery(Integer.class);
                Root<EmptyRoom> subRoot = subquery.from(EmptyRoom.class);


                if (hotelId != null) {
                    Join<KindOfRoom, Hotel> hotelJoin = root.join("hotel");
                    predicateList.add(criteriaBuilder.equal(hotelJoin.get("id"), hotelId));
                }
                // Add predicates to the subquery
                List<Predicate> subPredicates = new ArrayList<>();
                subPredicates.add(criteriaBuilder.equal(subRoot.get("kindOfRoom"), root));
                if (emptyRoom != null) {
                    subPredicates.add(criteriaBuilder.greaterThanOrEqualTo(subRoot.get("emptyRoom"), emptyRoom));
                }
                if (priceStart != null) {
                    subPredicates.add(criteriaBuilder.greaterThanOrEqualTo(subRoot.get("price"), priceStart));
                }
                if (priceEnd != null) {
                    subPredicates.add(criteriaBuilder.lessThanOrEqualTo(subRoot.get("price"), priceEnd));
                }
                if (status != null) {
                    subPredicates.add(criteriaBuilder.equal(root.get("status"), status));
                }
                if (provinceId !=null)
                {
                    Join<KindOfRoom,Hotel> hotelJoin = root.join("hotel",JoinType.INNER);
                    subPredicates.add(criteriaBuilder.equal(hotelJoin.get("province").get("id"),provinceId));
                }
                if (wardId !=null)
                {
                    Join<KindOfRoom,Hotel> hotelJoin = root.join("hotel",JoinType.INNER);
                    subPredicates.add(criteriaBuilder.equal(hotelJoin.get("ward").get("id"),wardId));
                }
                if (districtId !=null)
                {
                    Join<KindOfRoom,Hotel> hotelJoin = root.join("hotel",JoinType.INNER);
                    subPredicates.add(criteriaBuilder.equal(hotelJoin.get("district").get("id"),districtId));
                }


                subquery.select(criteriaBuilder.literal(1)).where(subPredicates.toArray(new Predicate[0]));

                predicateList.add(criteriaBuilder.exists(subquery));

                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            }
        };
    }
}
