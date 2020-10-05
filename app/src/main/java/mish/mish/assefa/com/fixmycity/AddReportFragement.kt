package mish.mish.assefa.com.fixmycity

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.util.Base64
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.change_password.*
import kotlinx.android.synthetic.main.fragement_add_report.*
import kotlinx.android.synthetic.main.image_selector.view.*
import com.google.android.gms.common.util.IOUtils.toByteArray
import mish.mish.assefa.com.fixmycity.Retrofit.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.*
import java.util.HashMap


const val GALLERY_REQUEST:Int=100
const val CAMERA_REQUEST:Int=200
class AddReportFragement:Fragment() {
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    private var retrofitInterface: IMyService? = null

    private var selectedImageUri: Uri? = null

   // var report = Report()
    //lateinit var imageUri:Uri
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragement_add_report, container, false)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                //val selectedImage: Uri? = data?.data
                selectedImageUri = data?.data
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                val imageStream: InputStream = this.requireContext().contentResolver.openInputStream(selectedImageUri)
                report_image_iv.setImageBitmap(BitmapFactory.decodeStream(imageStream))
                //report.image = selectedImageUri.toString()

            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val extra: Bundle? = data?.extras
            val image: Bitmap = extra?.get("data") as Bitmap
            report_image_iv.setImageBitmap(image)
            //report.image = data?.data.toString()

        }


    }

    val report=Report()

    override fun onStart() {
        super.onStart()
        retrofitInterface = retrofit!!.create(IMyService::class.java)


        val spinner: Spinner = reportname_spinner
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.requireActivity(),
            R.array.report_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                reportname_etv.setText(parent?.getItemAtPosition(position).toString())
            }
        }

        //val desc=report_description_etv.text.toString()
        //desc= arrayOf(InputFilter.LengthFilter(100))


        val spinCity: Spinner = reportlocation_spinner
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.requireActivity(),
            R.array.report_location,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            // Apply the adapter to the spinner
            spinCity.adapter = adapter
        }
        spinCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                reportlocation_etv.setText(parent?.getItemAtPosition(position).toString())
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this.requireActivity())



        add_image_btn.setOnClickListener {

            val view: View = LayoutInflater.from(this.requireActivity()).inflate(R.layout.image_selector, null)
            var builder2: AlertDialog.Builder = AlertDialog.Builder(this.requireActivity())
            builder2.setView(view)
            builder2.create().show()
            view.from_gallery_btn.setOnClickListener {
                var intent: Intent = Intent()
                intent.setType("image/")
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST)

            }
            view.from_camera_btn.setOnClickListener {
                val intent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(this.activity?.packageManager) != null) {
                    startActivityForResult(intent, CAMERA_REQUEST)
                }
            }


        }

        add_report_btn.setOnClickListener {

            val rName = reportname_etv.text.toString()
            val rLocation = reportlocation_etv.text.toString()
            val rSpinner: String = reportname_spinner.toString()
            val image = report_image_iv
            val desc = report_description_etv.editText.toString()


            report.desc = desc
            report.reportName = rName
            report.location=rLocation
            val map = HashMap<String, String>()
            map["name"] = rName
            map["description"] = desc


            val sessionManagement = SessionManagement(this.requireContext())
            val isLoggedIn: String = sessionManagement.getSession()
            map["userEmail"]=sessionManagement.getEmail()
            if (selectedImageUri == null) {
                view?.snackbar("Select an Image First")

            }


            val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r")

            val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
            val file = File(activity?.cacheDir, activity?.contentResolver?.getFileName(selectedImageUri!!))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)


            if (isLoggedIn.isNotEmpty()) {
               // val user=LoginResult()

                val body = UploadImage.UploadRequestBody(file, "image")
                val call = retrofitInterface!!.addReport(
                  file.path,
                    map
                )
                call.enqueue(object : Callback<Report> {
                    override fun onFailure(call: Call<Report>, t: Throwable) {

                        builder.setMessage("Unable to add to the Send the Report!!!+${t.printStackTrace()}")
                            .setTitle("Submitted")
                            .setCancelable(true)
                            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

                                //  Action for 'NO' Button
                                dialog.cancel()


                            })


                        val alert = builder.create()
                        alert.show()

                        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(resources.getColor(R.color.colorPrimary))

                        //Toast.makeText(this@AddReportFragement,"Unable to add to the Database",Toast.LENGTH_LONG).
                    }

                    override fun onResponse(call: Call<Report>, response: Response<Report>) {
                        if (response.code() == 200) {
                            report.image=response.body()!!.image
                            report.reportName=response.body()!!.reportName
                            report.desc=response.body()!!.desc
                            report.municip_by=response.body()!!.municip_by
                            report.reported_by=response.body()!!.reported_by

                            builder.setMessage("Your report has been submitted succesfully!!!").setTitle("Submitted")
                                .setCancelable(true)
                                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->

                                    //  Action for 'NO' Button
                                    dialog.cancel()


                                })


                            val alert = builder.create()
                            alert.show()

                            alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(resources.getColor(R.color.colorPrimary))

                            val int: Intent = Intent(activity, NewRequestFragement()::class.java)
                            activity?.startActivity(int)
                            //val intent:Intent=Intent(this@AddReportFragement,RequestActivity::class.java)


                        } else if (response.code() == 400) {
                            report_description_etv.editText?.setText("")
                            reportlocation_etv.setText("")
                            reportname_etv.setText("")
                            Toast.makeText(activity, "Report invalid refill the fields", Toast.LENGTH_LONG)

                        }
                    }

                })
                //val iMyService:IMyService=RetrofitClient.getInstance()


            }
        }

        }



    }//on start



