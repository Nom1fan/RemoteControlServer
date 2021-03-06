package com.mmerhav.remotecontrolserver.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StopProcessResult {

    private final boolean isSuccess;

    private String errMsg;
}
