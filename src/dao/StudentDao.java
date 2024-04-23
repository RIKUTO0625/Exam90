package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao  extends Dao{

	/**
	 * getメソッド 学生番号を指定して学生インスタンスを1件取得する
	 *
	 * @param no:String
	 *            学生番号
	 * @return 学生クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */

	public Student get(String no) throws Exception{

		Student student =  new Student();

		//データベースのコネクションを確立
		//データベースに接続された(Connectionによって)
		Connection connection = getConnection();

		//プリペアードステートメント
		//SQL文をデータべースに送るためのオブジェクト
		PreparedStatement statement = null;

		try{

			//SQLをセットする
			statement = connection.prepareStatement("select * from student where student_no=? ");

			//？（プレースホルダー）の値にセットする
			statement.setString(1,no);

			//プリペアードステートメントを実行
			ResultSet rSet=statement.executeQuery();

			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if(rSet.next()){

				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				//検索結果をセット
				student.setNo(rSet.getString("student_no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));

				//学生フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
			}else{
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				student= null;
			}

		}catch(Exception e){

			throw e;

		}finally{
			//プリペアステートメントを閉じる
			if(statement!=null){
				try{
					statement.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}

			//コネクションを閉じる
			if(connection!=null){
				try{
					connection.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}
		}

		return student;

	}


	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from student where school_cd=? ";

	private List<Student> postFilter(ResultSet rSet, School school) throws Exception{

		List<Student> list = new ArrayList<>();

		try{

			while(rSet.next()){

				//学生インスタンス
				Student student = new Student();

				//検索結果をセット
				student.setNo(rSet.getString("student_no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);

				//
				list.add(student);
			}
		}catch(SQLException | NullPointerException e ){

			e.printStackTrace();
		}

		return list;
	}


	/**
	 *
	 * @param school
	 * @param entYear
	 * @param classNum
	 * @param isAttend
	 * @return　学生のリストList<Student>
	 * @throws Exception
	 */
	public List<Student> filter(School school,int entYear,String classNum,boolean isAttend) throws Exception{

		//戻り値用のリストを作成
		//new演算子とArrayListで空のListを用意
		List<Student> list = new ArrayList<>();

		//データベースのコネクションを確立
		//データベースに接続された(Connectionによって)
		Connection connection = getConnection();

		//プリペアードステートメント
		//SQL文をデータべースに送るためのオブジェクト
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String condition = "and ent_year=? and class_num=? ";

		String order = " order by student_no asc";

		String conditionIsAttend = "";

		if(isAttend){
			conditionIsAttend = "and is_attend=true";
		}
		try{

			//SQLをセットする
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);

			//？（プレースホルダー）の値にセットする
			statement.setString(1,school.getCd());

			statement.setInt(2, entYear);

			statement.setString(3,classNum);

			//プリペアードステートメントを実行
			rSet=statement.executeQuery();

			//リストへの格納処理を実行
			list = postFilter(rSet,school);

		}catch(Exception e){

			throw e;

		}finally{
			//プリペアステートメントを閉じる
			if(statement!=null){
				try{
					statement.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}

			//コネクションを閉じる
			if(connection!=null){
				try{
					connection.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}
		}

		return list;

	}

    /**
     *
     * @param school
     * @param entYear
     * @param isAttend
     * @return 学生のリスト:List<Student>　存在しない場合は０件
     * @throws Exception
     */
	public List<Student>filter(School school,int entYear,boolean isAttend) throws Exception{

		//戻り値用のリストを作成
		//new演算子とArrayListで空のListを用意
		List<Student> list = new ArrayList<>();

		//データベースのコネクションを確立
		//データベースに接続された(Connectionによって)
		Connection connection = getConnection();

		//プリペアードステートメント
		//SQL文をデータべースに送るためのオブジェクト
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String condition = "and ent_year=? ";

		String order = " order by student_no asc";

		String conditionIsAttend = "";

		if(isAttend){
			conditionIsAttend = "and is_attend=true";
		}
		try{

			//SQLをセットする
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);

			//？（プレースホルダー）の値にセットする
			statement.setString(1,school.getCd());

			statement.setInt(2, entYear);

			//プリペアードステートメントを実行
			rSet=statement.executeQuery();

			//リストへの格納処理を実行
			list = postFilter(rSet,school);

		}catch(Exception e){

			throw e;

		}finally{
			//プリペアステートメントを閉じる
			if(statement!=null){
				try{
					statement.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}

			//コネクションを閉じる
			if(connection!=null){
				try{
					connection.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}
		}

		return list;
	}


	/**
	 *
	 * @param school
	 * @param isAttend
	 * @return 学生のリスト:List<Student>　存在しない場合は０件
	 * @throws Exception
	 */
	public List<Student> filter(School school,boolean isAttend) throws Exception{

		//戻り値用のリストを作成
		//new演算子とArrayListで空のListを用意
		List<Student> list = new ArrayList<>();

		//データベースのコネクションを確立
		//データベースに接続された(Connectionによって)
		Connection connection = getConnection();

		//プリペアードステートメント
		//SQL文をデータべースに送るためのオブジェクト
		PreparedStatement statement = null;

		ResultSet rSet = null;

		String order = " order by student_no asc";

		String conditionIsAttend = "";

		if(isAttend){
			conditionIsAttend = "and is_attend=true ";
		}
		try{

			//SQLをセットする
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);

			//？（プレースホルダー）の値にセットする
			statement.setString(1,school.getCd());

			//プリペアードステートメントを実行
			rSet=statement.executeQuery();

			//リストへの格納処理を実行
			list = postFilter(rSet,school);

		}catch(Exception e){

			throw e;

		}finally{
			//プリペアステートメントを閉じる
			if(statement!=null){
				try{
					statement.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}

			//コネクションを閉じる
			if(connection!=null){
				try{
					connection.close();
				}catch(SQLException sqle){
					throw sqle;
				}
			}
		}

		return list;
	}


	public boolean save (Student student) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// データベースから学生を取得
			Student old = get (student.getNo ());
			if(old == null) {
				// 学生が存在しなかった場合
				// プリペアードステートメンにINSERT文をセットと
				statement = connection. prepareStatement (
				"insert into student (student_no,name,ent_year,class_num,is_attend,school_cd) values(?, ?, ?, ?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1,student.getNo()) ;
				statement.setString (2,student.getName()) ;
				statement.setInt (3,student.getEntYear()) ;
				statement.setString (4,student.getClassNum()) ;
				statement.setBoolean(5,student.isAttend());
				statement.setString(6,student.getSchool().getCd ());
			}else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
				.prepareStatement ("update student set name=?,ent_year=?,class_num=?,is_attend=? where student_no=? ");
				// プリペアードステートメントに値をバインド
				statement.setString(1,student.getName());
				statement.setInt(2,student.getEntYear());
				statement. setString (3, student.getClassNum());
				statement. setBoolean(4, student.isAttend());
				statement. setString (5, student.getNo ()) ;

			}

			//プリペアードステートメントを実行
			count = statement.executeUpdate ();

		}catch(Exception e) {
				throw e;
		}finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement. close ();
				}catch (SQLException sqle) {
					throw sqle;
				}
			}
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close ();
				}catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		}else{
		    //実行件数が0件の場合
		    return false;
		}

	}

}
