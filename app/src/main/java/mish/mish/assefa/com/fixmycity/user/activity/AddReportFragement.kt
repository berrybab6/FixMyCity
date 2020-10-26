package mish.mish.assefa.com.fixmycity.user.activity

import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragement_add_report.*
import kotlinx.android.synthetic.main.fragement_add_report.view.*
import kotlinx.android.synthetic.main.image_selector.view.*
import mish.mish.assefa.com.fixmycity.R
import mish.mish.assefa.com.fixmycity.Retrofit.*
import mish.mish.assefa.com.fixmycity.data.report.ReportReq
import mish.mish.assefa.com.fixmycity.user.data.UploadImage
import mish.mish.assefa.com.fixmycity.user.data.controller.SessionClass
import mish.mish.assefa.com.fixmycity.user.data.getFileName
import mish.mish.assefa.com.fixmycity.user.data.snackbar
import mish.mish.assefa.com.fixmycity.user.data.user.User

import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.*
import java.time.LocalDate
import java.time.Month
import java.util.*


const val GALLERY_REQUEST:Int=100
const val CAMERA_REQUEST:Int=200
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddReportFragement: Fragment() {
    private lateinit var session: SessionClass
   // lateinit var municipalities: Municipalities
    private lateinit var adapter1:ArrayAdapter<String>
    private lateinit var adapter:ArrayAdapter<String>
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
                //report.photo_url = selectedImageUri.toString()

            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val extra: Bundle? = data?.extras
            //selectedImageUri=
            selectedImageUri=data!!.data
            val image: Bitmap = extra?.get("data") as Bitmap
            view?.report_image_iv?.setImageBitmap(image)


        }
    }

    lateinit var report:ReportReq

    override fun onStart() {
        super.onStart()

        session= SessionClass(this.requireContext())

        val isLoggedIn = session.isLoggedIn()
        session.checkLogin()

        //val data = arguments!!.getString("user")
        retrofitInterface = retrofit!!.create(IMyService::class.java)


       val call = retrofitInterface!!.getMunicip()
/*
        call.enqueue(object:Callback<ArrayList<String>>{
            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                var a= mutableListOf<String>("ads","asaSASAS")
                municipalities= Municipalities(a)
                Toast.makeText(activity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                if(!response.isSuccessful){
                    Toast.makeText(activity,response.code(),Toast.LENGTH_LONG).show()
                    return
                }
                val list=response.body()
                val lisM=mutableListOf<String>()
                    for (i in 0 until list!!.size) {
                        lisM.add(list[i])
                    }
                municipalities=Municipalities(lisM)
            }
        })
*/

            val municipality = resources.getStringArray(R.array.report_location)
            adapter1 = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item,municipality)

            val spinner = reportname_spinner//reportname_spinner
            val names = resources.getStringArray(R.array.report_array)

// Create an ArrayAdapter using the string array and a default spinner layout
            adapter = ArrayAdapter(
                this.requireActivity(),
                android.R.layout.simple_spinner_item, names
            )
            // Specify the layout to use when the list of choices appears
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

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
        spinCity.adapter=adapter1
// Create an ArrayAdapter using the string array and a default spinner layout

            // Specify the layout to use when the list of choices appears
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


            // Apply the adapter to the spinner

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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                    GALLERY_REQUEST
                )


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
            val desc = report_description_etv.text.toString()

            val user = session.getUserDetails()
            val userEmail= user.getValue(session.KEY_EMAIL)
            val f=user.getValue(session.KEY_NAME)
            val l=user.getValue(session.KEY_LNAME)
            val e=user.getValue(session.KEY_EMAIL)
            val u=user.getValue(session.KEY_USERNAME)
            val i=user.getValue(session.KEY_ID)
            val p=user.getValue(session.KEY_PASSWORD)
            val token=user.getValue(session.KEY_SESSION)
            val user1= User(f, l, p, e, i, u, token)



          //map["userEmail"]=sessionManagement.getEmail()
            if (selectedImageUri == null) {
                view?.snackbar("Select an Image First")
            }

             val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r")

            val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
            val file = File(activity?.cacheDir, activity?.contentResolver?.getFileName(selectedImageUri!!))
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)


            val map = HashMap<String, String>()
            map["name"] = rName
            map["description"] = desc
            map["user_id"]=i
            map["municipal"]=rLocation
           // map["session"]=user1

           map["image"]=file.name

               // val user=LoginResult()

          //  map2["user"]=sessionManagement.getSession()
                val body = UploadImage.UploadRequestBody(file, "image")
                val call1 = retrofitInterface!!.addReport(token,
                    //MultipartBody.Part.createFormData("image", file.name, body),
                    //RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "json"),
                    map
                )
                call1.enqueue(object : Callback<ReportReq> {

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onFailure(call: Call<ReportReq>, t: Throwable) {
                        //t.message
                        //val date = LocalDate.of(2016, Month.APRIL, 15) as Date
                        report= ReportReq("Lorem Ipsum erek earn kgk pl ogk oks adel o mj safaris screwier ideal n,cabin jeff faff","Dead Animal",
                            "Bole Sub City","sd","Misha",
                            false,
                            false,
                            null,
                            false,
                            "0"
                        )
                        builder.setMessage(t.localizedMessage)
                            .setTitle("why")
                            .setCancelable(true)
                            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                                //  Action for 'NO' Button
                                dialog.cancel()
                            })


                        val alert = builder.create()
                        alert.show()

                        alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                            .setTextColor(resources.getColor(R.color.colorPrimary))
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(call: Call<ReportReq>, response: Response<ReportReq>) {
                        if (response.code() == 200){
                            val photo=response.body()!!.photo_url
                            val name=response.body()!!.name
                            val desc=response.body()!!.description
                            val reported_by=response.body()!!.reported_by
                            val reported_to=response.body()!!.reported_to
                            val reported_At=response.body()!!.created_at

                            report=ReportReq(
                                desc,
                                name,
                                reported_to,
                                photo,
                                reported_by,
                                false,false,
                                reported_At,
                                false,
                                "0hr ago"
                            )

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

                            val int = Intent(activity, RequestActivity()::class.java)
                            startActivity(int)


                            //val intent:Intent=Intent(this@AddReportFragement,RequestActivity::class.java)


                        } else if (response.code() == 400) {
                            report_description_etv?.setText("")
                            reportlocation_etv?.setText("")
                            reportname_etv?.setText("")
                          //  val date = LocalDate.of(2016, Month.APRIL, 15) as Date

                            report= ReportReq("Lorem Ipsum erek earn kgk pl ogk oks adel o mj safaris screwier ideal n,cabin jeff faff","Dead Animal",
                                "Bole Sub City","sd","Misha",
                                false,
                                false,
                                null,
                                false,
                                "0"
                            )

                            Toast.makeText(activity, rLocation, Toast.LENGTH_LONG).show()

                        }
                    }

                })
                //val iMyService:IMyService=RetrofitClient.getInstance()




            Toast.makeText(this.requireContext(),userEmail,Toast.LENGTH_LONG).show()
        }

        }



    }//on start



