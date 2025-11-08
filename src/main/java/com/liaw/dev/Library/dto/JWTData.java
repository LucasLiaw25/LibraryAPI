package com.liaw.dev.Library.dto;

import lombok.Builder;

@Builder
public record JWTData(Long id, String name, String email) {
}
