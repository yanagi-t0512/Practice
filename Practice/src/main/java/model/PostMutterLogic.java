package model;

import dao.MutterDao;

public class PostMutterLogic {

	public void execute(Mutter mutter) {
		MutterDao dao = new MutterDao();
		dao.create(mutter);
	}
}
