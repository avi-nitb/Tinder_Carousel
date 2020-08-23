package com.tindercarousel.pojo;

import java.util.List;

public class UserData {
    private List<Results> results;

    public List<Results> getResults() {
        return results;
    }

    public class Results{
        private User user;

        public User getUser() {
            return user;
        }

        public class User{
            private String gender;
            private Name name;
            private Location location;
            private String email;
            private String username;
            private String dob;
            private String phone;
            private String picture;

            public String getGender() {
                return gender;
            }

            public Name getName() {
                return name;
            }

            public Location getLocation() {
                return location;
            }

            public String getEmail() {
                return email;
            }

            public String getUsername() {
                return username;
            }

            public String getDob() {
                return dob;
            }

            public String getPhone() {
                return phone;
            }

            public String getPicture() {
                return picture;
            }

            public class Name{
                private String title;
                private String first;
                private String last;

                public String getTitle() {
                    return title;
                }

                public String getFirst() {
                    return first;
                }

                public String getLast() {
                    return last;
                }
            }

            public class Location{
                private String street;
                private String city;
                private String state;
                private String zip;

                public String getStreet() {
                    return street;
                }

                public String getCity() {
                    return city;
                }

                public String getState() {
                    return state;
                }

                public String getZip() {
                    return zip;
                }
            }
        }
    }
}
