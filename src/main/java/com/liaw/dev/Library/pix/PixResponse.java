package com.liaw.dev.Library.pix;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PixResponse {
    Long loanId;
    String txid;
    String pixCopiaECola;
    BigDecimal value;
}
