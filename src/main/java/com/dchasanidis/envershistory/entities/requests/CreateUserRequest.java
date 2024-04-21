package com.dchasanidis.envershistory.entities.requests;

import com.dchasanidis.envershistory.entities.model.Address;

public record CreateUserRequest(String name, String email, Address address) {
}
