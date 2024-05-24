package happy.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarcodeController {

    private static final Logger logger = LoggerFactory.getLogger(BarcodeController.class);

    @PostMapping("/barcode")
    public String receiveBarcode(@RequestBody String barcode) {
        logger.info("Received barcode: {}", barcode);
        return "Barcode received: " + barcode;
    }
}
