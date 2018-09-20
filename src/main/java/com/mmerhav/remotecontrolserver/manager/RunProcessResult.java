package com.mmerhav.remotecontrolserver.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RunProcessResult {

    private final boolean isSuccess;

    private String errMsg;
}
