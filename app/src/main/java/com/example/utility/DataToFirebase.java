package com.example.utility;

public class DataToFirebase {

//    public void insertRuleList() {
//        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_RULE_MASTER");
//
//        RuleList rl = new RuleList();
//        rl.setRULEID(1);
//        rl.setFINEAMT(214);
//        rl.setRULE_DESC("Forgot to bring a driver's license");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(2);
//        rl.setFINEAMT(338);
//        rl.setRULE_DESC("Speeding");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(3);
//        rl.setFINEAMT(237);
//        rl.setRULE_DESC("Regulations of turns at intersections");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(4);
//        rl.setFINEAMT(284);
//        rl.setRULE_DESC("Valid Registration");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(5);
//        rl.setFINEAMT(796);
//        rl.setRULE_DESC("Uninsured car in accident");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(6);
//        rl.setFINEAMT(533);
//        rl.setRULE_DESC("Did not stop at red light or turning right");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(7);
//        rl.setFINEAMT(675);
//        rl.setRULE_DESC("Pass the flashing light of school bus");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(8);
//        rl.setFINEAMT(248);
//        rl.setRULE_DESC("Passing or Overtaking another Vehicle");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(9);
//        rl.setFINEAMT(425);
//        rl.setRULE_DESC("Crossing double yellow line");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(10);
//        rl.setFINEAMT(650);
//        rl.setRULE_DESC("Driving after license was suspended or revoked due to DUI");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(11);
//        rl.setFINEAMT(284);
//        rl.setRULE_DESC("Violation of turn or turn around");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(12);
//        rl.setFINEAMT(284);
//        rl.setRULE_DESC("Stop sign but did not stop");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(13);
//        rl.setFINEAMT(100);
//        rl.setRULE_DESC("Drinking and Driving");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(14);
//        rl.setFINEAMT(489);
//        rl.setRULE_DESC("Failing to stop at Rail Road crossing");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(15);
//        rl.setFINEAMT(250);
//        rl.setRULE_DESC("Parking in loading/unloading zone");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(16);
//        rl.setFINEAMT(178);
//        rl.setRULE_DESC("Covering car door");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(17);
//        rl.setFINEAMT(976);
//        rl.setRULE_DESC("Stop at bus stop");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(17);
//        rl.setFINEAMT(150);
//        rl.setRULE_DESC("Holding cellphone while driving");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(18);
//        rl.setFINEAMT(436);
//        rl.setRULE_DESC("Child did not wear a seat belt or not in a child seat");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(19);
//        rl.setFINEAMT(160);
//        rl.setRULE_DESC("Not wearing a seat belt");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//
//        rl.setRULEID(20);
//        rl.setFINEAMT(178);
//        rl.setRULE_DESC("Driving with headphones ON");
//        rl.setSTATE("California");
//
//        reff.push().setValue(rl);
//    }

//    public void insertCarData() {
//        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("TT_CARS_METADATA");
//
//        CarMetaData cmd = new CarMetaData();
//
//        cmd.setCar_MODEL("BMW");
//        cmd.setCar_NUMBER("7LHC252");
//        cmd.setOwner_MAIL("upasana.garg@student.csulb.edu");
//        cmd.setOwner_NAME("Upasana Garg");
//        cmd.setOwner_PHONE("5625522872");
//        cmd.setSeqid(1);
//        cmd.setSsn("545-00-0212");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Bentley");
//        cmd.setCar_NUMBER("5SAM123");
//        cmd.setOwner_MAIL("gargupasana06@gmail.com");
//        cmd.setOwner_NAME("Upasana");
//        cmd.setOwner_PHONE("5625522872");
//        cmd.setSeqid(2);
//        cmd.setSsn("545-00-1212");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Jaguar");
//        cmd.setCar_NUMBER("6PIV728");
//        cmd.setOwner_MAIL("aditi.tomar@student.csulb.edu");
//        cmd.setOwner_NAME("Aditi Tomar");
//        cmd.setOwner_PHONE("5624728253");
//        cmd.setSeqid(3);
//        cmd.setSsn("545-11-1212");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Tesla");
//        cmd.setCar_NUMBER("7MMLG976");
//        cmd.setOwner_MAIL("aditidvlp@gmail.com");
//        cmd.setOwner_NAME("Aditi");
//        cmd.setOwner_PHONE("5624728253");
//        cmd.setSeqid(4);
//        cmd.setSsn("560-11-1212");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Tata");
//        cmd.setCar_NUMBER("8Y37123");
//        cmd.setOwner_MAIL("sonu.jacob@student.csulb.edu");
//        cmd.setOwner_NAME("Sonu Anna Jacob");
//        cmd.setOwner_PHONE("6196425196");
//        cmd.setSeqid(5);
//        cmd.setSsn("560-11-2000");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Austo Martin");
//        cmd.setCar_NUMBER("5XRT063");
//        cmd.setOwner_MAIL("upasana.garg@student.csulb.edu");
//        cmd.setOwner_NAME("Helly Patel");
//        cmd.setOwner_PHONE("5622416998");
//        cmd.setSeqid(6);
//        cmd.setSsn("560-10-2000");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Lamborgini");
//        cmd.setCar_NUMBER("5633W");
//        cmd.setOwner_MAIL("upasana.garg@student.csulb.edu");
//        cmd.setOwner_NAME("Test User");
//        cmd.setOwner_PHONE("9999999999");
//        cmd.setSeqid(7);
//        cmd.setSsn("594-11-2000");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Bentley");
//        cmd.setCar_NUMBER("3SAM123");
//        cmd.setOwner_MAIL("aditi.tomar@student.csulb.edu");
//        cmd.setOwner_NAME("Bentley Owner");
//        cmd.setOwner_PHONE("5625522872");
//        cmd.setSeqid(8);
//        cmd.setSsn("594-11-2110");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Jaguar");
//        cmd.setCar_NUMBER("CYCR966");
//        cmd.setOwner_MAIL("aditi.tomar@student.csulb.edu");
//        cmd.setOwner_NAME("Jaguar Owner");
//        cmd.setOwner_PHONE("5624728253");
//        cmd.setSeqid(9);
//        cmd.setSsn("594-11-4410");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Honda");
//        cmd.setCar_NUMBER("1RSF628");
//        cmd.setOwner_MAIL("anurag.singh@student.csulb.edu");
//        cmd.setOwner_NAME("Anurag Singh");
//        cmd.setOwner_PHONE("5623367873");
//        cmd.setSeqid(10);
//        cmd.setSsn("594-56-4410");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Honda");
//        cmd.setCar_NUMBER("65718K");
//        cmd.setOwner_MAIL("lovenish@student.csulb.edu");
//        cmd.setOwner_NAME("Lovenish");
//        cmd.setOwner_PHONE("5623368573");
//        cmd.setSeqid(11);
//        cmd.setSsn("594-56-4444");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Toyota");
//        cmd.setCar_NUMBER("6XUW811");
//        cmd.setOwner_MAIL("subhashree@student.csulb.edu");
//        cmd.setOwner_NAME("Subhashree");
//        cmd.setOwner_PHONE("4623368573");
//        cmd.setSeqid(12);
//        cmd.setSsn("594-56-0000");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//
//        cmd.setCar_MODEL("Benz");
//        cmd.setCar_NUMBER("5NGD658");
//        cmd.setOwner_MAIL("aroma@student.csulb.edu");
//        cmd.setOwner_NAME("Aroma");
//        cmd.setOwner_PHONE("4624428573");
//        cmd.setSeqid(13);
//        cmd.setSsn("594-56-0001");
//        cmd.setState("California");
//        cmd.setZipcode(90815);
//        reff.push().setValue(cmd);
//    }
}
