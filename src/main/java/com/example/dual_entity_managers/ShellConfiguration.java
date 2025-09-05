package com.example.dual_entity_managers;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellConfiguration {

    private final DualEntityManagersService dualEntityManagersService;

    public ShellConfiguration(DualEntityManagersService dualEntityManagersService) {
        this.dualEntityManagersService = dualEntityManagersService;
    }

    @ShellMethod
    public void first(String data, String extraData) {
        dualEntityManagersService.first(data, extraData);
    }

    @ShellMethod
    public void second(String data, String extraData) {
        dualEntityManagersService.second(data, extraData);
    }

}
