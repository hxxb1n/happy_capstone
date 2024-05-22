package happy.server.service;

import happy.server.entity.Parcel;
import happy.server.repository.ParcelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public String myOrderParcelTrackingNumber(Long orderId) {
        Parcel parcel = parcelRepository.findParcelByOrderId(orderId);
        return parcel.getTrackingNumber();
    }

}
