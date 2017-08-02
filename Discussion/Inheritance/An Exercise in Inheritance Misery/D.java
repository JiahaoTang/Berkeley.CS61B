class D {
	public static void main (String[] args) {
		B a0 = new A(); //Error.
		a0.m1();
		a0.m2(16);
		A b0 = new B();
		System.out.println(b0.x);//print out 5
		b0.m1();//Am1->5
		b0.m2();//Bm2->5
		b0.m2(61);//Bm2y->61
		B b1 = new B();
		b1.m2(61);//Bm2y->61
		b1.m3();//Bm3->Called
		A c0 = new C();
		c0.m2();//Cm2->5
		C c1 = (A) new C();//Error.
		A a1 = (A) c0;
		C c2 = (C) a1;
		c2.m3();//Bm3->Called
		c2.m4();//Cm4->5
		c2.m5();//Cm5->6
		((C) c0).m3();//Bm3->Called
		(C) c0.m3();//Error.
		b0.update();
		b0.m1();//Am1->99
	}
}
