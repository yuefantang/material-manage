package com.dongyu.test.deliveryNote;

import com.dongyu.company.deliverynote.dao.DeliveryNoteDao;
import com.dongyu.company.deliverynote.domain.DeliveryNote;
import com.dongyu.company.deliverynote.service.DeliveryNoteService;
import com.dongyu.test.MaterialWebTestApplication;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO:请添加描述
 *
 * @author TYF
 * @date 2019/5/11
 * @since 1.0.0
 */
public class DeliveryNoteTest extends MaterialWebTestApplication {
    @Autowired
    private DeliveryNoteDao deliveryNoteDao;
    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @Test
    public void collectionTest() {
        DeliveryNote newest = deliveryNoteDao.findNewest();
        // deliveryNoteService.deleted(6L);
    }

}
