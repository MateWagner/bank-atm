package org.example.atm.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class LogoutController {
  @PostMapping("logout")
  fun logout() {
  }
}
