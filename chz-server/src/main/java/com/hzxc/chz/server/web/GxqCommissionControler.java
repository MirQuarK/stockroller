package com.hzxc.chz.server.web;

import com.hzxc.chz.service.DistributionLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GxqCommissionControler extends AbstractControler {
    private static Logger logger = LoggerFactory.getLogger(GxqCommissionControler.class);

    @Autowired
    DistributionLock distributionLock;
}
