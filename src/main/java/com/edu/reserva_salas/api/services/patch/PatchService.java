package com.edu.reserva_salas.api.services.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

@Component
public class PatchService {

    public static <T> void globalPatch(Map<String, Object> fields, T classe){
        ObjectMapper objectMapper = new ObjectMapper();
        T classeConverte = objectMapper.convertValue(fields, (Class<T>) classe.getClass());
        fields.forEach((chave, valor)-> {
            Field field = ReflectionUtils.findField(classe.getClass(), chave);
            field.setAccessible(true);

            Object novoObjeto =  ReflectionUtils.getField(field, classeConverte);

            ReflectionUtils.setField(field, classe, novoObjeto);

            field.setAccessible(false);
        });
    }
}
