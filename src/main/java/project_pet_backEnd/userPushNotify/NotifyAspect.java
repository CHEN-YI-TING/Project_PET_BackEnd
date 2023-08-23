package project_pet_backEnd.userPushNotify;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project_pet_backEnd.userPushNotify.dao.PictureInfoRepository;
import project_pet_backEnd.userPushNotify.vo.PictureInfo;
import project_pet_backEnd.utils.AllDogCatUtils;

@Aspect
@Component
public class NotifyAspect {
    @Autowired
    private PictureInfoRepository pictureInfoRepository;
    @Autowired
    private  UserNotifyWebSocketHandler userNotifyWebSocketHandler;
    //To Do: 美容師 新增作品時
    @After("execution(* project_pet_backEnd.groomer.groomerworkmanager.service.*.insert(..))")
    public  void  groomerUpdateNotify() throws Exception {
             NotifyType notifyType =NotifyType.Groomer;
             PictureInfo pictureInfo= pictureInfoRepository.findFirstByOrderByPiDateDesc();
             NotifyMsg notifyMsg =new NotifyMsg(notifyType, AllDogCatUtils.base64Encode(pictureInfo.getPiPicture()),"有美容師發布作品了，趕快來看看喔~");
             userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
    }
    //To Do: 商城 新增商品時
    @After("execution(* project_pet_backEnd.user.controller.*.*(..))")
    public  void  productUpdateNotify() throws Exception {
        NotifyType notifyType =NotifyType.Store;
        NotifyMsg notifyMsg =new NotifyMsg(notifyType,null,"商城有新的商品，趕快來看看喔~");
        System.out.println("執行 groomerUpdateNotify");userNotifyWebSocketHandler.publicNotifyMsg(notifyMsg);
    }

}
