import com.ensta.interfacegraphique.exception.ServiceException;
import com.ensta.interfacegraphique.model.Personnel;
import com.ensta.interfacegraphique.service.PersonnelService;

import java.util.List;

public class ImplTest {
    public static void main(String args[]) throws ServiceException {
        PersonnelService personnelService = PersonnelService.getInstance();
        try{
            List<Personnel> personnelList = personnelService.getByString("00");
        }catch (ServiceException e){
            System.out.println("error");
        }
    }
}
