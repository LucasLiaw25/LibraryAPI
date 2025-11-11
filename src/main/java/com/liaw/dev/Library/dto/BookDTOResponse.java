package com.liaw.dev.Library.dto;


import com.liaw.dev.Library.entity.Loan;
import com.liaw.dev.Library.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
}
