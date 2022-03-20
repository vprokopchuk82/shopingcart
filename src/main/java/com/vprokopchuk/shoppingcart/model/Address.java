package com.vprokopchuk.shoppingcart.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "postalCode")
    private String postalCode;
    @Column(name = "town")
    private String town;
    @Column(name = "citySubDivision")
    private String citySubDivision;
    @Column(name = "country")
    private String country;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    public Address(
            String id,
            String address1,
            String address2,
            String postalCode,
            String town,
            String citySubDivision,
            String country,
            String email,
            String phone
    ) {
        this.id = id;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.town = town;
        this.citySubDivision = citySubDivision;
        this.country = country;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address() {
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCitySubDivision() {
        return citySubDivision;
    }

    public void setCitySubDivision(String citySubDivision) {
        this.citySubDivision = citySubDivision;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id.equals(address.id) &&
                email.equals(address.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", town='" + town + '\'' +
                ", citySubDivision='" + citySubDivision + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


    public static class Builder {
        private String id;
        private String address1;
        private String address2;
        private String postalCode;
        private String town;
        private String citySubDivision;
        private String country;
        private String email;
        private String phone;

        public Address.Builder withId(String id) {
            this.id = id;
            return this;
        }
        public Address.Builder withAddress1(String address1) {
            this.address1 = address1;
            return this;
        }

        public Address.Builder withAddress2(String address2) {
            this.address2 = address2;
            return this;
        }

        public Address.Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address.Builder withTown(String town) {
            this.town = town;
            return this;
        }

        public Address.Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Address.Builder withCitySubDivision(String citySubDivision) {
            this.citySubDivision = citySubDivision;
            return this;
        }

        public Address.Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Address.Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Address build(){
            return new Address(
                    id,
                    address1,
                    address2,
                    postalCode,
                    town,
                    citySubDivision,
                    country,
                    email,
                    phone

            );


        }




    }
}
