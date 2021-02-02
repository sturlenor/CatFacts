package no.stunor.catfacts.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import no.stunor.catfacts.R
import no.stunor.catfacts.model.Cat
import no.stunor.catfacts.model.CatFact


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_fact)
        val imageView: ImageView = root.findViewById(R.id.image_cat)
        val randomButton: Button = root.findViewById(R.id.button_random)
        homeViewModel.getCatFacts()?.observe(viewLifecycleOwner, Observer<CatFact?> { catFact ->
            if (catFact != null) {
                textView.text = catFact.text
            }
        })

        homeViewModel.getCat()?.observe(viewLifecycleOwner, Observer<Cat?> { cat ->
            if (cat != null) {
                Picasso.get()
                    .load(cat.url)
                    .into(imageView)
            }
        })
        randomButton.setOnClickListener {
            homeViewModel.getRandomCat()
        }
        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_share -> {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, Uri.parse(homeViewModel.getCat()?.value?.url))
                    putExtra(Intent.EXTRA_TEXT, homeViewModel.getCatFacts()?.value?.text)
                    type = "image/*";
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivity(Intent.createChooser(shareIntent, resources.getText(R.string.title_share)))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
