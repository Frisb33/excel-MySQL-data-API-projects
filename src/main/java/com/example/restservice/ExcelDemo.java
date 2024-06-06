package com.example.restservice;

import jakarta.persistence.Entity;
import org.springframework.data.annotation.Id;

@Entity
public class ExcelDemo {

    @jakarta.persistence.Id private int STT;
    private String Fields;
    private String Path;
    private String Description;
    private String Code;
    private String ObjectType;
    private int Length;
    private String VCBDescription;
    private String FISDescription;


    public ExcelDemo(int STT, String fields, String path, String description, String code,
                     String objectType, int length, String VCBDescription, String FISDescription) {
        this.STT = STT;
        this.Fields = fields;
        this.Path = path;
        this.Description = description;
        this.Code = code;
        this.ObjectType = objectType;
        this.Length = length;
        this.VCBDescription = VCBDescription;
        this.FISDescription = FISDescription;
    }

    public ExcelDemo() {

    }


    public String toString()
    {
        return String.format("%d - %s - %s - %s - %s - %s - %d - %s" +
                        " - %s",
                STT,
                Fields, Path, Description, Code, ObjectType, Length,
                VCBDescription, FISDescription );
    }

    public int getSTT() {
        return STT;
    }

    public String getFields() {
        return Fields;
    }

    public String getPath() {
        return Path;
    }

    public String getDescription() {
        return Description;
    }

    public String getCode() {
        return Code;
    }

    public String getObjectType() {
        return ObjectType;
    }

    public int getLength() {
        return Length;
    }

    public String getVCBDescription() {
        return VCBDescription;
    }

    public String getFISDescription() {
        return FISDescription;
    }
}

