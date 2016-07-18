package com.architecture.dao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class DataBaseDaoGenerator {

    private final Schema schema;

    public DataBaseDaoGenerator() {
        //Schema Entities belong to a schema. A schema is the first object you define. Call the constructor with the schema version and the default Java package:
        schema = new Schema(1, "com.architecture");
        //The default Java package is used when greenDAO generates entities, DAOs, and JUnit tests. If those defaults are fine for your project, you are done with the first step.
        //If you want the DAO and test classes to go into separate packages, can refine your schema like this:
        schema.setDefaultJavaPackageDao("com.architecture.biz.dao");
        schema.setDefaultJavaPackageTest("com.architecture.biz.entity");

        //The schema also has two default flags for entities, which can be overridden. The flags tell if entities are active, and if keep sections should be used. Those features are not yet documented; have a look at the test project in the source code distribution.
        //schema.enableKeepSectionsByDefault();
        //schema.enableActiveEntitiesByDefault();
        createSimple();
//        createProvince();
//        createCity();
//        createCustomType();
//        createKeepSections();
    }

    public void generate() throws Exception {
        DaoGenerator daoGenerator = new DaoGenerator();
        daoGenerator.generateAll(schema, "app/src-gen", "app/src-gen", "app/src/androidTest/java");
    }

    /**
     * 创建简单实体类
     */
    protected void createSimple() {
        Entity simple = schema.addEntity("SimpleEntity");
        simple.addIdProperty().autoincrement();
        simple.addBooleanProperty("simpleBoolean");
        simple.addByteProperty("simpleByte");
        simple.addShortProperty("simpleShort");
        simple.addIntProperty("simpleInt");
        simple.addLongProperty("simpleLong");
        simple.addFloatProperty("simpleFloat");
        simple.addDoubleProperty("simpleDouble");
        simple.addStringProperty("simpleString");
        simple.addByteArrayProperty("simpleByteArray");

        simple.addContentProvider().readOnly();
    }

    protected void createProvince() {
        Entity province = schema.addEntity("Province");

        //添加java doc
        province.setJavaDoc("This entity is used by internal tests of greenDAO.\n" +
                "(This JavaDoc is defined in the generator project.)");
        //添加类注释
        province.setCodeBeforeClass("// This is another test comment, you could also apply annotations like this");

        //ID 添加属性注释
        province.addIdProperty().javaDocField("JavaDoc test field");
        //名称 添加get set 方法注释
        province.addStringProperty("name").javaDocGetterAndSetter("province name");
        //.codeBeforeField("@SerializedName(\"the-number-of-things\")")属性前添加代码，如“注解”
        //别名
        province.addStringProperty("alias");
        //城市数量
        province.addIntProperty("cityQuantity");
        //人口数量
        province.addLongProperty("populationQuantity");
        //排名
        province.addIntProperty("Ranking");
    }

    protected void createCity() {
        Entity city = schema.addEntity("City");
        //ID
        city.addIdProperty();
        //名称
        city.addStringProperty("name");
        //是否是直辖市
        city.addBooleanProperty("isTerritory");
        //是否是省会
        city.addBooleanProperty("isCapital");
        //人口数量
        city.addLongProperty("populationQuantity");
        //省ID
        city.addLongProperty("provinceID");
    }

    /**
     * 自定义数据库字段类型
     */
    protected void createCustomType() {
        Entity entity = schema.addEntity("CustomTypeEntity");
        entity.addIdProperty();
        entity.addLongProperty("myCustomTimestamp").customType("com.example.lz.android_greendao_sample.customtype.MyTimestamp",
                "com.example.lz.android_greendao_sample.customtype.MyTimestampConverter");
    }

    /**
     * 创建保持属性，再次创建实体时，不会覆盖掉保持的属性和方法
     */
    protected void createKeepSections() {
        Entity entity = schema.addEntity("KeepEntity");
        //schema.enableKeepSectionsByDefault();
        //or
        entity.setHasKeepSections(true);

        entity.addIdProperty();
        entity.addStringProperty("type");
        entity.addStringProperty("name");
    }

    public static void main(String[] args) throws Exception {
        DataBaseDaoGenerator daoGenerator = new DataBaseDaoGenerator();
        daoGenerator.generate();
    }
}
