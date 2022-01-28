package com.trainingmanagementserver.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Objects;

@Table(name = "userDetails")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;

    @NotEmpty(message = "first name cannot be null")
    @Size(min = 3, max = 30, message = "first name must be of 3 to 30 letters long")
    private String first_name;

    @Size(min = 3, max = 15, message = "last name must be of 3 to 30 letters long")
    @NotEmpty(message = "last name cannot be null")
    private String last_name;

    private String profile_pic_url;

    @Pattern(regexp = "^([0-2][0-9]|[3][0-1])$")
    @Size(min = 2, max = 2, message = "birth date must be of 2 digits")
    @NotEmpty(message = "Birth date cannot be null")
    private String birth_date;

    @Pattern(regexp = "^([0][1-9]|[1][0-2])$")
    @Size(min = 2, max = 2, message = "birth month must be of 2 digits")
    @NotEmpty(message = "birth month cannot be null")
    private String birth_month;

    @Pattern(regexp = "^([1][9][6-9][0-9]|[2][0][0-2][0-2])$")
    @NotEmpty(message = "birth year cannot be null")
    @Size(min = 4, max = 4, message = "birth year must be of 2 digits")
    private String birth_year;

    @NotEmpty(message = "area cannot be null")
    @Size(min = 3, max = 20, message = "area must be of 3 to 20 letter")
    private String area;

    @NotEmpty(message = "city cannot be null")
    @Size(min = 3, max = 20, message = "city must be of 3 to 20 letter")
    private String city;

    @NotEmpty(message = "district cannot be null")
    @Size(min = 3, max = 20, message = "district must be of 3 to 20 letter")
    private String district;

    @NotEmpty(message = "pin code cannot be null")
    @Size(min = 6, max = 6, message = "pincode must be of 6 digits")
    private int pin_code;

    @NotEmpty(message = "mobile_number")
    @Pattern(regexp = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$")
    public String mobile_number;

    @NotEmpty(message = "gender")
    @Pattern(regexp = "^([m|M][a|A][l|L][e|E]|[f|F][e|E][m|M][a|A][l|L][e|E])$")
    public String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsEntity that = (UserDetailsEntity) o;
        return id == that.id && pin_code == that.pin_code && Objects.equals(first_name, that.first_name)
                && Objects.equals(last_name, that.last_name) && Objects.equals(birth_date, that.birth_date)
                && Objects.equals(birth_month, that.birth_month) && Objects.equals(birth_year, that.birth_year)
                && Objects.equals(area, that.area) && Objects.equals(city, that.city)
                && Objects.equals(district, that.district) && Objects.equals(mobile_number, that.mobile_number)
                && Objects.equals(gender, that.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, birth_date, birth_month, birth_year, area, city, district,
                pin_code, mobile_number, gender);
    }
}
