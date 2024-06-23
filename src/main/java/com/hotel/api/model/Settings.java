package com.hotel.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "db_settings")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Settings extends Auditable<String>{

    @Column(name = "setting_key", unique =  true)
    private String settingKey;
    @Column(name = "setting_value", columnDefinition = "text")
    private String settingValue;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "is_system")
    private Integer isSystem;
    @Column(name = "is_editable")
    private Integer isEditable;
}
