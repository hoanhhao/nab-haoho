package com.nab.dto;

import lombok.Data;

@Data
public class UserDto {
  private String login;
  private String token;

  public UserDto() {

  }

  public UserDto(String login, String token) {
    this.login = login;
    this.token = token;
  }
}
