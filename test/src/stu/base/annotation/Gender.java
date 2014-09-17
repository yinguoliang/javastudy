package stu.base.annotation;

public enum Gender {
	MAN{
		public String getName(){return "ÄÐ";}
	},
	WOMAN{
		public String getName(){return "Å®";}
	};
	public abstract String getName();
}
