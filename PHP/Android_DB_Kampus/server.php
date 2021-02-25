<?php
 
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','db_kampus');
$con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');

@$operasi = $_GET['operasi'];

switch ($operasi) {
    case "view":
        /* Source code untuk Menampilkan Biodata */
	
	$sql = "SELECT * FROM dosen";
	$query_tampil_biodata = mysqli_query($con,$sql);
        	$data_array = array();
        	while ($data = mysqli_fetch_assoc($query_tampil_biodata)) {
            $data_array[] = $data;
            }
        echo json_encode($data_array);
        break;

    case "insert":
        /* Source code untuk Insert data */
        @$nidn = $_GET['NIDN'];
        @$nama_dosen = $_GET['Nama_Dosen'];
        @$pend_akhir = $_GET['Pend_Terakhir'];
        $sql = "INSERT INTO dosen (NIDN,Nama_Dosen,Pend_Terakhir) VALUES('$nidn','$nama_dosen', '$pend_akhir')";
	$query_insert_data = mysqli_query($con,$sql);

        if ($query_insert_data) {
            echo "Data Berhasil Disimpan";
        } else {
            echo "Error Inser DB_Kampus " . mysql_error();
        }
        break;
   
   case "get_dosen_by_id":
        /* Source code untuk Edit data dan mengirim data berdasarkan id yang diminta */
        @$id = $_GET['id'];
	$sql = "SELECT * FROM `dosen` WHERE id='$id'";
        $query_tampil_matakuliah= mysqli_query($con,$sql);
        $data_array = array();
        $data_array = mysqli_fetch_assoc($query_tampil_matakuliah);
        echo "[" . json_encode($data_array) . "]";
        break;

    case "update":
        /* Source code untuk Update Biodata */
        @$nidn = $_GET['NIDN'];
        @$nama_dosen = $_GET['Nama_Dosen'];
        @$pend_akhir = $_GET['Pend_Terakhir'];
        @$id = $_GET['id'];
	$sql = "UPDATE dosen SET NIDN='$nidn', Nama_Dosen='$nama_dosen', Pend_Terakhir='$pend_akhir' WHERE id='$id'";
	$query_update_dosen = mysqli_query($con,$sql);

        if ($query_update_dosen) {
            echo "Update Data Berhasil";
        } else {
            echo mysql_error();
        }
        break;

    case "delete":
        /* Source code untuk Delete Biodata */
        @$id = $_GET['id'];
	$sql = "DELETE FROM dosen WHERE id='$id'";
	$query_delete_biodata = mysqli_query($con,$sql);
        
        if ($query_delete_biodata) {
            echo "Delete Data Berhasil";
        } else {
            echo mysql_error();
        }

        break;

    default:
        break;
}
?>
