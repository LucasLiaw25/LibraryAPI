package com.liaw.dev.Library.service;

import com.liaw.dev.Library.dto.LoanDTO;
import com.liaw.dev.Library.mapper.LoanMapper;
import com.liaw.dev.Library.mapper.UserMapper;
import com.liaw.dev.Library.repository.LoanRepository;
import com.liaw.dev.Library.repository.UserRepository;
import com.liaw.dev.Library.validator.LoanValidator;
import com.liaw.dev.Library.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanMapper mapper;
    private final LoanValidator validator;
    private final LoanRepository repository;

    public void createLoan(){

    }

}
