package stu.base.annotation;

public enum Gender {
	MAN{
		public String getName(){return "��";}
	},
	WOMAN{
		public String getName(){return "Ů";}
	};
	public abstract String getName();
}
