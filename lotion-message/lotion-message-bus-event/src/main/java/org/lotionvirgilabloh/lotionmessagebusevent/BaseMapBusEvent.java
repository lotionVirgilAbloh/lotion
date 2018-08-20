package org.lotionvirgilabloh.lotionmessagebusevent;

import org.lotionvirgilabloh.lotionbase.dto.BaseMapDTO;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class BaseMapBusEvent extends RemoteApplicationEvent {

    public BaseMapDTO baseMapDTO;

    public BaseMapBusEvent(BaseMapDTO baseMapDTO) {
        this.baseMapDTO = baseMapDTO;
    }

    public BaseMapBusEvent(Object source, String originService, String destinationService, BaseMapDTO baseMapDTO) {
        super(source, originService, destinationService);
        this.baseMapDTO = baseMapDTO;
    }

    public BaseMapBusEvent(Object source, String originService, BaseMapDTO baseMapDTO) {
        super(source, originService);
        this.baseMapDTO = baseMapDTO;
    }

    public BaseMapDTO getBaseMapDTO() {
        return baseMapDTO;
    }

    public void setBaseMapDTO(BaseMapDTO baseMapDTO) {
        this.baseMapDTO = baseMapDTO;
    }
}
