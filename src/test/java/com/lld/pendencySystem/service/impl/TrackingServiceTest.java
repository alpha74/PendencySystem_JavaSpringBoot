package com.lld.pendencySystem.service.impl;

import com.lld.pendencySystem.entity.Tag;
import com.lld.pendencySystem.enums.TagType;
import com.lld.pendencySystem.repository.EntityRepo;
import com.lld.pendencySystem.repository.CTagRepo;
import com.lld.pendencySystem.repository.impl.EntityRepoImpl;
import com.lld.pendencySystem.repository.impl.CTagRepoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TrackingServiceTest {
    private EntityRepo entityRepo;
    private CTagRepo CTagRepo;
    private TrackingServiceImpl trackingService;

    @BeforeEach
    public void setup() {
        entityRepo = new EntityRepoImpl();
        CTagRepo = new CTagRepoImpl();
        trackingService = new TrackingServiceImpl(entityRepo, CTagRepo);
    }

    @Test
    public void testFlow() {
        /*
            Test Case 1

            startTracking(1112, \["UPI", "Karnataka", "Bangalore"\]);
            startTracking(2451, \["UPI", "Karnataka", "Mysore"\]);
            startTracking(3421, \["UPI", "Rajasthan", "Jaipur"\]);
            startTracking(1221, \["Wallet", "Karnataka", "Bangalore"\]);

            getCounts(\["UPI"\]) # represents the counts of all pending "UPI" transactions
            Output: 3
            getCounts(\["UPI", "Karnataka"\]) # represents the counts of all pending "UPI" transactions in "Karnataka"
            Output: 2
            getCounts(\["UPI", "Karnataka", "Bangalore"\]) # represents the counts of all pending "UPI" transactions in "Karnataka" and "Bangalore"
            Output: 1

            getCounts(\["Bangalore"\]) # Does not represent a valid hierarchy in the sample
            Output: 0
         */

        Tag UPITag = new Tag("UPI", TagType.INSTRUMENT);
        Tag WalletTag = new Tag("Wallet", TagType.INSTRUMENT);

        Tag BangaloreTag = new Tag("Bangalore", TagType.CITY);
        Tag JaipurTag = new Tag("Jaipur", TagType.CITY);
        Tag MysoreTag = new Tag("Mysore", TagType.CITY);

        Tag KarnatakaTag = new Tag("Karnataka", TagType.STATE);
        Tag RajasthanTag = new Tag("Rajasthan", TagType.STATE);

        trackingService.startTracking("1112", Arrays.asList(UPITag, KarnatakaTag, BangaloreTag));
        trackingService.startTracking("2451", Arrays.asList(UPITag, KarnatakaTag, MysoreTag));
        trackingService.startTracking("3421", Arrays.asList(UPITag, RajasthanTag, JaipurTag));
        trackingService.startTracking("1221", Arrays.asList(WalletTag, KarnatakaTag, BangaloreTag));

        assert trackingService.getCounts(Arrays.asList(UPITag)) == 3;
        assert trackingService.getCounts(Arrays.asList(UPITag, KarnatakaTag)) == 2;
        assert trackingService.getCounts(Arrays.asList(UPITag, KarnatakaTag, BangaloreTag)) == 1 ;

        assert trackingService.getCounts(Arrays.asList(BangaloreTag)) == 0;

        /*
            Test Case 2

            startTracking(4221, \["Wallet", "Karnataka", "Bangalore"\]);
            stopTracking(1112);
            stopTracking(2451);

            getCounts(\["UPI"\])
            Output: 1
            getCounts(\["Wallet"\])
            Output: 2
            getCounts(\["UPI", "Karnataka", "Bangalore"\])
            Output: 0
         */

        trackingService.startTracking("4221", Arrays.asList(WalletTag, KarnatakaTag, BangaloreTag));
        trackingService.stopTracking("1112");
        trackingService.stopTracking("2451");

        assert trackingService.getCounts(Arrays.asList(UPITag)) == 1 ;
        assert trackingService.getCounts(Arrays.asList(WalletTag)) == 2;
        assert trackingService.getCounts(Arrays.asList(UPITag, KarnatakaTag, BangaloreTag)) == 0;
    }

}
