package shareClasses;

public enum NodeType {
	SYSTEM(0),
	DISEASE(1),
	MEDICINE_GROUP(2),
	GENERIC(3),
	MEDICINE_BRAND(4),
	CHEAP_COMPLAIN(1),
	CHEAP_COMPLAIN_CAUSE(2),
	INVESTIGATION(2),
	INVESTIGATION_GROUP(1),
	ADVISE(2),
	ADVISE_GROUP(1),
	CLINICAL_EXAMINATION(1),
	CLINICAL_EXAMINATION_RESULT(2),
	SURVEY_QUESTION(2);
	int type;
	public int getType(){
		return this.type;
	}
	
	private NodeType(int type){
		this.type = type;
	}
}
