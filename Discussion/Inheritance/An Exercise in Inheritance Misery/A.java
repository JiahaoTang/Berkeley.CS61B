class A {
	public int x = 5;
	public void m1() {System.out.println("Am1-> " + x);}
	public void m2() {System.out.println("Am2-> " + this.x);}
	public void update() {x = 99;}
}

class B extends A {
	public void m2() {System.out.println("Bm2-> " + x);}
	public void m2(int y) {System.out.println("Bm2y-> " + y);}
	public void m3() {System.out.println("Bm3-> " + "Called");}
}

class C extends B {
	public int y = x + 1;
	public void m2() {System.out.println("Cm2-> " + super.x);}
	public void m4() {System.out.println("Cm4-> " + super.super.x);}
	public void m5() {System.out.println("Cm5-> " + y);}
}