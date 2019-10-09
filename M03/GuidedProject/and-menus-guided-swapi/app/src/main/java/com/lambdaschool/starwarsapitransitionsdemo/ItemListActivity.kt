package com.lambdaschool.starwarsapitransitionsdemo

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.ViewCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.*

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    private var swApiObjects: ArrayList<SwApiObject>? = null
    private var viewAdapter: SimpleItemRecyclerViewAdapter? = null

    private var drawerLayout: DrawerLayout? = null

    private var currentType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        // this will assign our toolbar xml to be the system toolbar
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        // TODO 3: get handle to drawer layout and bind to toolbar toggle

        swApiObjects = ArrayList()

        if (findViewById<View>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        val recyclerView = findViewById<View>(R.id.item_list)
        assert(recyclerView != null)
        setupRecyclerView(recyclerView as RecyclerView)

        // TODO 6: create a menu item selection listener and bind it to our navigation view
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        swApiObjects?.let {
            viewAdapter = SimpleItemRecyclerViewAdapter(this, it, mTwoPane)
            recyclerView.adapter = viewAdapter
            //        getData();
        }
    }

    internal inner class PeopleGetter(var start: Int, var offset: Int) : Runnable {

        override fun run() {
            var person: Person?
            var counter = start
            var failCount = 0
            do {
                person = SwApiDao.getPerson(counter)
                counter += offset

                if (person != null && currentType == TYPE_PEOPLE) {
                    swApiObjects!!.add(person)
                    //                        dbDao.createPerson(person);
                    runOnUiThread { viewAdapter!!.notifyItemChanged(swApiObjects!!.size - 1) }
                    failCount = 0
                } else {
                    //                        final List<Person> allPeople = dbDao.getAllPeople();
                    ++failCount
                }
            } while ((person != null || failCount < 2) && currentType == TYPE_PEOPLE)
        }
    }

    private fun getData(type: Int) {
        currentType = type
        when (type) {
            TYPE_PEOPLE -> for (i in 0..4) {
                Thread(PeopleGetter(i + 1, 5)).start()
            }
            TYPE_PLANETS -> {
                var counter = 1
                SwApiDao.getPlanet(counter++, object : SwApiDao.SwApiCallback {
                    override fun processObject(`object`: SwApiObject?) {
                        if (`object` != null) {
                            swApiObjects!!.add(`object`)
                            runOnUiThread { viewAdapter!!.notifyItemChanged(swApiObjects!!.size - 1) }
                        }
                    }
                })
            }
            TYPE_STARSHIPS -> Thread(Runnable {
                var starship: Starship?
                var counter = 1
                var failCount = 0
                do {
                    starship = SwApiDao.getStarship(counter++)
                    if (starship != null) {
                        swApiObjects!!.add(starship)
                        runOnUiThread { viewAdapter!!.notifyItemChanged(swApiObjects!!.size - 1) }
                        failCount = 0
                    } else {
                        ++failCount
                    }
                } while (starship != null || failCount < 5)
            }).start()
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     *
     * This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * [.onPrepareOptionsMenu].
     *
     *
     * The default implementation populates the menu with standard system
     * menu items.  These are placed in the [Menu.CATEGORY_SYSTEM] group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     *
     * You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     *
     * When you add items to the menu, you can implement the Activity's
     * [.onOptionsItemSelected] method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see .onPrepareOptionsMenu
     *
     * @see .onOptionsItemSelected
     */
    // TODO 7: add our menu to the toolbar menu

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     *
     * Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param menuItem The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see .onCreateOptionsMenu
     */

    // TODO 8: Add logic to listener

    class SimpleItemRecyclerViewAdapter

    internal constructor(private val mParentActivity: ItemListActivity,
                         private val mValues: List<SwApiObject>,
                         private val mTwoPane: Boolean) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private var lastPosition = -1


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val swApiObject = mValues[position]
            holder.mIdView.text = swApiObject.id.toString()
            holder.mNameView.text = swApiObject.name

            holder.mCategoryView.text = swApiObject.category
            holder.mImageView.setImageDrawable(
                    holder.mImageView.context.getDrawable(
                            DrawableResolver.getDrawableId(
                                    swApiObject.category ?: "",
                                    swApiObject.id)))

            holder.itemView.tag = swApiObject
            holder.itemView.setOnClickListener { view ->
                // S04M03-17 update click listener to pass our object
                val item = view.tag as SwApiObject
                if (mTwoPane) {
                    val arguments = Bundle()
                    //                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(item.getId()));  // put object in intent
                    arguments.putSerializable(ItemDetailFragment.ARG_ITEM_ID, item)
                    val fragment = ItemDetailFragment()
                    fragment.arguments = arguments
                    mParentActivity.supportFragmentManager.beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val context = view.context
                    val intent = Intent(context, ItemDetailActivity::class.java)
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item)  // put object in intent

                    val options = ActivityOptions.makeSceneTransitionAnimation(
                            view.context as Activity,
                            holder.mImageView,
                            ViewCompat.getTransitionName(holder.mImageView)
                    ).toBundle()

                    context.startActivity(intent, options)
                }
            }

            // S04M03-15 call animation method
            setEnterAnimation(holder.parentView, position)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        private fun setEnterAnimation(viewToAnimate: View, position: Int) {
            if (position > lastPosition) {
                val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
                viewToAnimate.startAnimation(animation)
                lastPosition = position
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mIdView: TextView
            val mNameView: TextView
            val mCategoryView: TextView
            val mImageView: ImageView
            val parentView: View

            init {
                mIdView = view.findViewById<View>(R.id.id_text) as TextView
                mNameView = view.findViewById<View>(R.id.name) as TextView
                mCategoryView = view.findViewById(R.id.category)
                mImageView = view.findViewById(R.id.image_view)
                parentView = view.findViewById(R.id.parent_view)
            }
        }
    }

    companion object {

        private val TYPE_PEOPLE = 1
        private val TYPE_PLANETS = 2
        private val TYPE_STARSHIPS = 3
    }
}
