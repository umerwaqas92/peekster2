package com.peekster.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.couchbase.lite.*
import com.peekster.Interfaces.clicked_last_item
import com.peekster.R
import com.peekster.Utils.Utills
import com.peekster.database.movie.database_utilss
import kotlinx.android.synthetic.main.activity_main_2.view.*
import kotlinx.android.synthetic.main.item_category.view.*
import java.util.function.Consumer
import java.util.logging.Handler

class Category_adapter (val context: Context,val isChild:Boolean): RecyclerView.Adapter<Category_adapter.ViewHolder>() {

    val list:ArrayList<Result> = arrayListOf();

    lateinit var db:Database
    lateinit var listner:clicked_last_item;
     var isDialog:Boolean=false;

    fun setListiner(l:clicked_last_item):Category_adapter{
        listner=l
        return this
    }
    fun setDialoge(b:Boolean):Category_adapter{
        isDialog=b
        return this
    }


    fun refresh_data(){
        list.clear()
        db=database_utilss.getDatabase(context)
        android.os.Handler().post({
            val query = QueryBuilder.select(
                SelectResult.expression(Meta.id),
                SelectResult.property("title"),
                SelectResult.property("type"),
                SelectResult.property("forChild")
            ).from(DataSource.database(db))
                .where(Expression.property("type").equalTo(Expression.string("category"))
                    .and((Expression.property("forChild").equalTo(Expression.booleanValue(isChild)))))
            val result=query.execute();


            result.allResults().forEach({ result: Result? ->
                list.add(result!!)

            })


            notifyDataSetChanged()
            Log.e(Utills.tag,"Loaded "+list.size)



        })
    }
    init {

        refresh_data()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category,parent,false))
    }

    override fun getItemCount(): Int {
        if(isDialog){
            return list.size
        }
        return list.size+1
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        if(position>=list.size){

            holder.textView.setText("Add New")
            holder.textView.setOnClickListener({
                if(listner!=null){
                    listner.clicked(list.get(0).getString("id"),true)
                }
            })


            return
        }else{
//            if(list.get(position).getBoolean("Selected")){
//                        holder.textView.setBackgroundColor(Color.parseColor("#C2185B"))

                holder.textView.setText("Selected")
                Log.e(Utills.tag,"selected"+position)

//            }else{
                holder.textView.setText(list.get(position).getString("title"))

            }




            holder.textView.setOnClickListener(View.OnClickListener { view ->
                //            holder.textView.setBackgroundColor(Color.parseColor("#C2185B"))

                if(isDialog){
                    Log.e(Utills.tag,"clicked ")
                    if(listner!=null){
                        listner.clicked(list.get(position).getString("id"),false)
                    }

                    return@OnClickListener
                }

                if(listner!=null){
                    listner.clicked(list.get(position).getString("id"),false)
                }


                list.forEach({ result: Result ->
                    var doc:MutableDocument=db.getDocument(result.getString("id")).toMutable()
                    doc.setBoolean("Selected",false)
                    db.save(doc)

                    Log.e(Utills.tag,"selected false "+result.getString("title"))

                })

                var doc:MutableDocument=db.getDocument(list.get(position).getString("id")).toMutable()
                doc.setBoolean("Selected",true)
                db.save(doc)




                refresh_data()
                Log.e(Utills.tag,"selected set "+doc.getString("title"))



            })
        }





    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var textView:Button=view.item_category_btn
    }
}
