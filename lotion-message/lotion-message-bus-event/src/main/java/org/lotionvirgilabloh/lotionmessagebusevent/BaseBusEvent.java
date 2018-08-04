package org.lotionvirgilabloh.lotionmessagebusevent;

import org.lotionVirgilAbloh.lotionbase.dto.BaseDTO;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class BaseBusEvent extends RemoteApplicationEvent {

    public BaseDTO baseDTO;

    public BaseBusEvent(BaseDTO baseDTO) {
        this.baseDTO = baseDTO;
    }

    public BaseBusEvent(Object source, String originService, String destinationService, BaseDTO baseDTO) {
        super(source, originService, destinationService);
        this.baseDTO = baseDTO;
    }

    public BaseBusEvent(Object source, String originService, BaseDTO baseDTO) {
        super(source, originService);
        this.baseDTO = baseDTO;
    }

    public BaseDTO getBaseDTO() {
        return baseDTO;
    }

    public void setBaseDTO(BaseDTO baseDTO) {
        this.baseDTO = baseDTO;
    }
}
