package mish.mish.assefa.com.fixmycity

import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
//import android.support.v4.app.Fragement
//import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragement_add_report.*
import kotlinx.android.synthetic.main.fragement_add_report.view.*
import kotlinx.android.synthetic.main.image_selector.view.*
import mish.mish.assefa.com.fixmycity.Retrofit.*
import mish.mish.assefa.com.fixmycity.data.controller.SessionManagement
import mish.mish.assefa.com.fixmycity.data.controller.UploadImage
import mish.mish.assefa.com.fixmycity.data.controller.getFileName
import mish.mish.assefa.com.fixmycity.data.controller.snackbar
import mish.mish.assefa.com.fixmycity.data.report.ReportReq

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
class AddReportFragement: Fragment() {
    private var retrofit: Retrofit? = RetrofitClient.getInstance()
    private var retrofitInterface: IMyService? = null

    private var selectedImageUri: Uri? = null

   // var report = Report()
    //lateinit var imageUri:Uri
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       //val data:String? = arguments!!.getString("user")

        val view= inflater.inflate(R.layout.fragement_add_report, container, false)
       activity?.title = "Add Report"


           return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                //val selectedImage: Uri? = data?.data
                selectedImageUri = data?.data
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                val imageStream: InputStream = this.requireContext().contentResolver.openInputStream(selectedImageUri)
               view?.report_image_iv?.setImageBitmap(BitmapFactory.decodeStream(imageStream))
                //report.image = selectedImageUri.toString()

            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val extra: Bundle? = data?.extras
            val image: Bitmap = extra?.get("data") as Bitmap
            view?.report_image_iv?.setImageBitmap(image)
            //report.image = data?.data.toString()

        }


    }


    val report= ReportReq()

    override fun onStart() {
        super.onStart()

        //val data = arguments!!.getString("user")
        retrofitInterface = retrofit!!.create(IMyService::class.java)


        val spinner: Spinner = view!!.reportname_spinner
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
                reportname_etv?.setText(parent?.getItemAtPosition(position).toString())
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
                reportlocation_etv?.setText(parent?.getItemAtPosition(position).toString())
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this.requireActivity())



        add_image_btn?.setOnClickListener {

            val view: View = LayoutInflater.from(this.requireActivity()).inflate(R.layout.image_selector, null)
            var builder2: AlertDialog.Builder =AlertDialog.Builder(this.requireActivity())
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

        add_report_btn?.setOnClickListener {

            val rName = reportname_etv.text.toString()
            val rLocation = reportlocation_etv.text.toString()
            val rSpinner: String = reportname_spinner.toString()
            val image = report_image_iv
            val desc = report_description_etv.toString()




            report.description = desc
            report.name = rName
            report.municipal=rLocation
            val map = HashMap<String, String>()
            map["name"] = rName
            map["description"] = desc

            val sessionManagement =SessionManagement(this.requireContext())

            val isLoggedIn = sessionManagement.SESSION_KEY


          //  map["userEmail"]=sessionManagement.getEmail()
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

          //  map2["user"]=sessionManagement.getSession()
                val body = UploadImage.UploadRequestBody(file, "image")
                val call = retrofitInterface!!.addReport(

                   MultipartBody.Part.createFormData("image", file.name, body),
                    RequestBody.create(MediaType.parse("multipart/form-data"), "json"),

                    map
                   // map
                )
                call.enqueue(object : Callback<ReportReq> {
                    override fun onFailure(call: Call<ReportReq>, t: Throwable) {

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

                    override fun onResponse(call: Call<ReportReq>, response: Response<ReportReq>) {
                        if (response.code() == 200) {
                            report.image=response.body()!!.image
                            report.name=response.body()!!.name
                            report.description=response.body()!!.description
                            report.reported_by=response.body()!!.reported_by
                            report.municipal=response.body()!!.municipal

                            builder.setMessage("Your report has been submitted succesfully!!!").setTitle("Submitted")
                                .setCancelable(true)
                                .setNegativeButton("Cancel") { dialog, which ->

                                    //  Action for 'NO' Button
                                    dialog.cancel()


                                }


                            val alert = builder.create()
                            alert.show()

                            alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                .setTextColor(resources.getColor(R.color.colorPrimary))

                            val int = Intent(activity, NewRequestFragement()::class.java)
                            activity?.startActivity(int)


                            //val intent:Intent=Intent(this@AddReportFragement,RequestActivity::class.java)


                        } else if (response.code() == 401) {
                            report_description_etv?.setText("")
                            reportlocation_etv?.setText("")
                            reportname_etv?.setText("")
                            Toast.makeText(activity, "Report invalid refill the fields", Toast.LENGTH_LONG)

                        }
                    }

                })
                //val iMyService:IMyService=RetrofitClient.getInstance()


            }

            Toast.makeText(this.requireContext(),"Unable to add to the Database",Toast.LENGTH_LONG).show()
        }

        }



    }//on start



