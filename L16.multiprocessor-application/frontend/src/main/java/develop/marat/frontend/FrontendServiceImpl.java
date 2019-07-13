package develop.marat.frontend;

import develop.marat.ms.core.Address;
import develop.marat.ms.core.BaseMSService;
import develop.marat.ms.core.TransferObject;
import develop.marat.ms_clients_common.services.FrontendService;
import develop.marat.frontend.template.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class FrontendServiceImpl extends BaseMSService implements FrontendService {
    private final TemplateProcessor templateProcessor;


    public FrontendServiceImpl(Address address, TemplateProcessor templateProcessor) {
        super(address);
        this.templateProcessor = templateProcessor;
    }

    @Override
    public String render(String templateFileName, Map<String, TransferObject> templateData) {
        try {
            return this.templateProcessor.getPage(templateFileName, getConvertTemplateData(templateData));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> getConvertTemplateData(Map<String, TransferObject> templateData){
        if(templateData == null){
            return null;
        }

        Map<String, Object> convertTemplateData = new HashMap<>();
        for(Map.Entry<String, TransferObject> entry: templateData.entrySet()){
            TransferObject value = entry.getValue();
            Object convertValue = value.getConvertData();
            convertTemplateData.put(entry.getKey(), convertValue);
        }
        return convertTemplateData;
    }

}
