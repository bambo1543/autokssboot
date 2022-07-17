package de.promove.autokss.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

//@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//public class Distance extends AbstractBaseEntity {
public class Distance extends AbstractBaseEntity {

    private Date date;

    private Float value;

}
