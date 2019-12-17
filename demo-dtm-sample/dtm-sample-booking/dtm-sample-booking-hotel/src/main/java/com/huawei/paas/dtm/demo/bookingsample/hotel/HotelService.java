package com.huawei.paas.dtm.demo.bookingsample.hotel;

import com.huawei.middleware.dtm.client.context.DTMContext;
import com.huawei.middleware.dtm.client.tcc.annotations.DTMTccBranch;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestSchema(schemaId = "hotel")
@RequestMapping(value = "/hotel")
public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    @RequestMapping(value = "/bookRoom", method = RequestMethod.GET)
    @DTMTccBranch(identifier = "hotel", confirmMethod = "confirm", cancelMethod = "cancel")
    public void bookRoom() throws InterruptedException {
        LOGGER.info("book hotel room");
    }

    public void confirm() {
        LOGGER.info("{} - {} confirm hotel",
                DTMContext.getDTMContext().getGlobalTxId(),
                DTMContext.getDTMContext().getBranchTxId());

    }

    public void cancel() {
        LOGGER.info("{} - {} cancel hotel",
                DTMContext.getDTMContext().getGlobalTxId(),
                DTMContext.getDTMContext().getBranchTxId());
    }

}
