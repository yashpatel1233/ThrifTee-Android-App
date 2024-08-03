
package com.example.thrifteeapp.helpers;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.example.thrifteeapp.model.Bottoms;
import com.example.thrifteeapp.model.Clothing;
import com.example.thrifteeapp.model.Footwear;
import com.example.thrifteeapp.model.Tops;

public class DataProvider {

  private List<Clothing> clothingItems;
  private ShoppingCart shoppingCart;
  private FavouritesList favouritesList;
  private ClothingFilter clothingFilter;

  public DataProvider() {
    clothingItems = new ArrayList<>();
    shoppingCart = new ShoppingCart();
    favouritesList = new FavouritesList();
    clothingFilter = new ClothingFilter();

    clothingItems.add(new Tops("Nike T-shirt", 30.00, "The Nike Sportswear Club T-Shirt is made with our everyday cotton fabric and a classic fit for a familiar feel right out of the bag. An embroidered Futura logo on the chest provides a signature Nike look.",
            "M", "T-Shirts","Male",1));
    clothingItems.add(new Bottoms("Levi's - Regular Straight 607 Jeans", 50.00, "Straight cut Levi's in the perfect medium blue. These vintage look jeans are a wardrobe staple.\n" +
            "\n" +
            "Item Details:\n" +
            "\n" +
            "Brand: Levi's\n" +
            "Size: 40 (Waist 53 cm, Length 112 cm)\n" +
            "Colour: Denim Blue\n" +
            "Materials: 100% Cotton\n" +
            "Condition: Very Good",32, "Jeans","Female",2));
    clothingItems.add(new Footwear("Vans Sneakers", 20.30, "Easy tie up. A perfect addition to your wardrobe.  Normal wear.VANS us a popular brand known for its quality,  comfort and style \n" +
            "\n" +
            "Women size 8.5\n" +
            "Men size 7",9, "Sneakers","Male",3));
    clothingItems.add(new Tops("House of Errors Hoodie", 170.00, "VERY nice hoodie and insane quality\n" +
            "10/10 condition I’ve only tried it on, only one on any New Zealand marketplace\n" +
            "$639 on their website \n" +
            "Bit too big for me sadly so wanting to sell it to buy another size", "L", "Hoodies","Male",4));
    clothingItems.add(new Bottoms("Dickies Jorts", 70.00, "Dickies jorts\n" +
            "Good condition\n" +
            "Size 32",30, "Shorts","Male",5));
    clothingItems.add(new Tops("Shekou Tank Top", 25.00, "XS\n" +
            "\n" +
            "Does have a couple of faint marks on the front (possibly perfume/body spray) not that noticeable though", "S", "Tank Top", "Female", 6));
    clothingItems.add(new Bottoms("Lululemon leggings", 45.00, "Lilac leggings in near New condition. Size 6\n" +
            "\n", 30, "Leggings", "Female", 7));
    clothingItems.add(new Footwear("Diesel D-Santiago Boot Dress Boots", 230.00, "Diesel offers trendy and stylish models that look great with jeans." +
            " Diesel provides a great look but also provides comfort with a rubber sole allowing for everyday use. " +
            "When it comes to choosing an outfit Diesels are always a good way to go!",
            8, "Boots", "Male", 8));
    clothingItems.add(new Tops("Champion Grey Crewneck", 40.00, "Size Medium\n" +
            "Brand: Champion\n" +
            "\n" +
            "Measurements\n" +
            "Length: 63 cm\n" +
            "Pit to pit: 55 cm\n" +
            "\n" +
            "Great condition as pictured", "XL", "Sweatshirt", "Male", 9));
    clothingItems.add(new Bottoms("RPM Manufacturing Co. Sweatpants", 35.00, "\n" +
            "In near perfect condition apart from a small split in the pocket seam\n" +
            "\n" +
            "RRP $99.99", 34, "Sweatpants", "Womens", 10));

// Add more items following the same format, including variations in size, gender, and category

    clothingItems.add(new Tops("Tommy Hilfiger Dress Shirt", 55.00, "Channel your inner preppy cool with a Tommy Hilfiger dress shirt. " +
            "Crafted for a sharp look, it features a classic button-down collar and a sleek, tailored fit." +
            " Whether you choose a crisp white for timeless appeal or a subtle gingham pattern for added flair," +
            " this shirt is sure to become a versatile staple in your wardrobe.", "M", "T-Shirts", "Male", 11));
    clothingItems.add(new Bottoms("Country Road Black Dress Pants", 65.00, "Country road. Black dress pants. \n" +
            "Never worn, sorry no tags attached. \n" +
            "Zip side pockets. \n" +
            "Material 98% cotton, 2% elastane. \n" +
            "\n" +
            "Measurements: waist is 46 cm across the top of the pants in a straight line. Outside leg length from waist to hem is 109cm.",
            30, "Dress Pants", "Female", 12));
    clothingItems.add(new Footwear("Adidas Forum High Sneakers", 60.00, "Adidas forum high sneakers \n" +
            "Size us7 \n" +
            "In good condition \n" +
            "Just don’t wear anymore", 7, "Sneakers", "Male", 13));
    clothingItems.add(new Tops("Polo Ralph Lauren Dress Shirt", 40.00, "Polo Ralph Lauren Dress Shirt \n" +
            "\n" +
            "Men's size XL \n" +
            "\n" +
            "Excellent condition with no damage \n" +
            "\n" +
            "Bought from David Jones for $190, has hardly ever been worn so looking to sell \n" +
            "\n" +
            "Selling as surplus to requirements", "L", "Polo Shirt", "Male", 14));
    clothingItems.add(new Bottoms("Junya Watanabe Draped Skirt", 280.00, "Junya Watanabe comme des garçons\n" +
            "\n" +
            "No fabric content or size label\n" +
            "Approx size 8 - Small \n" +
            "\n" +
            "A stretch knit fabric similar to swimsuit material\n" +
            "\n" +
            "Each drape adds another layer to the skirt creating a fishtail hem with about 5 layers\n" +
            "\n" +
            "Good condition", 32, "Skirts", "Female", 15));

    clothingItems.add(new Tops("Zara Black Long Sleeve", 35.00, "Label has come off, but the top is a small / 8.\n" +
            "\n", "M", "Long Sleeve", "Female", 16));
    clothingItems.add(new Bottoms("Huffer Cargo Pants", 50.00, "Huffer cargo pants, size 6. Have been worn but are still in good condition. Close-up photo shows best colour match.\n" +
            "\n", 36, "Jeans", "Male", 17));
    clothingItems.add(new Footwear("Dr Martens Boots", 90.00, "Looking for a good quality boots? \n" +
            "Grab this barely used Dr. Martens womens boots in a very prestige condition! \n" +
            "\n" +
            "It comes with wonder balsam when you purchase it.\n" +
            "\n" +
            "Size 36 (EU) or size 5 (US)\n" +
            "Selling for only $90 ", 6, "Boots", "Female", 18));
    clothingItems.add(new Tops("Black Crop Top", 20.00, "Black Crop Top\n" +
            "Size 6/8\n" +
            "Shein\n" +
            "Very Good Condition", "XS", "Crop Top", "Female", 19));
    clothingItems.add(new Bottoms("Baggy Black Cargo Shorts", 45.00, "Relaxed black cargo shorts offer a comfortable, casual look. These roomy shorts feature a loose fit through the legs for all-day comfort. They come with multiple pockets, including classic cargo pockets on the sides for stashing essentials. " +
            "The black color ensures versatility, making them easy to pair with graphic tees, " +
            "tanks, or button-downs.", 34, "Shorts", "Male", 20));

    clothingItems.add(new Tops("Denim Jacket Blue Wash", 95.00, "In overall good condition, some wear/discoloration as shown\n" +
            "Chest and Length Measurements provided in photos. Arm Length taken from Cuff to Underarm, if present.",
            "S", "Jackets", "Male", 21));
    clothingItems.add(new Bottoms("Floral Skirt", 45.00, "\n" +
            "Lovely floral skirt by Cue. Size 8. The skirt is black with a green floral design. Fully lined.\n" +
            "\n" +
            "Item Details:\n" +
            "\n" +
            "Brand: Cue\n" +
            "Size: 8\n" +
            "Measurements: Waist: 35 cm, Length: 64 cm\n" +
            "Colour: Black, Green\n" +
            "Materials: Nylon, Cotton, Metal\n" +
            "Condition: Very Good. Please note the floral design is showing as a grey colour in photos. However the floral design is green." +
            " The green is closest to the middle colour in colour swatch photo attached", 30, "Skirts", "Female", 22));
    clothingItems.add(new Footwear("Adidas leather superstar sneakers", 70.00, "Classic adidas superstar sneakers\n" +
            "Purchased in London \n" +
            "Have been in storage ", 8,
            "Sneakers", "Male", 23));
    clothingItems.add(new Tops("Navy Blue Jacket", 46.00, "IN very good condition, fleece lined\n" +
            "Chest and Length Measurements provided in photos. Arm Length taken from Cuff to Underarm, if present.",
            "XS", "Jackets", "Male", 24));
    clothingItems.add(new Tops("Orange Champion Hoodie", 60.00, " Made with soft fleece for cozy warmth, it features the brand's signature \"C\" logo embroidered on the chest for a touch of heritage style. Whether you choose the classic Reverse Weave for a timeless look or a lighter fleece option for warmer days, " +
            "this hoodie offers Champion's quality and comfort in a versatile everyday essential.", "S", "Hoodies", "Female", 25));
    clothingItems.add(new Bottoms("Blue Wash Denim Jeans", 35.00, "In overall good condition, wear throughout. Some thin spots on thighs.", 35, "Jeans", "Female", 26));
    clothingItems.add(new Footwear("Leather Chelsea Boots", 120.00, "10/10 condition", 7, "Boots", "Female", 27));
    clothingItems.add(new Tops("Vintage Nike Hoodie", 65.00, "Vintage Nike Mens Small Travis Scott Swoosh Fire Flames Thrashed Hoodie Black\n" +
            "\n" +
            "Mens Sweater\n" +
            "\n" +
            "Has distressing with small holes on the front hand pouch. Has distressing on the cuffs. Custom fire/flames print", "L", "Hoodies", "Male", 28));
    clothingItems.add(new Bottoms("Embroidered Jeans", 40.00, "JEANS embroidered pants.\n" +
            "\n" +
            "Item Details:\n" +
            "\n" +
            "Brand: JEANS\n" +
            "Size: 26\n" +
            "Measurements: Length: 102 cm, Waist: 34 cm\n" +
            "Colour: Black\n" +
            "Materials: Denim\n" +
            "Condition: Very Good", 28, "Jeans", "Female", 29));
    clothingItems.add(new Footwear("Salamon Hiking Boots", 80.00, "Bought these to walk a Camino in Spain but decided to walk in different shoes so used once and in near new condition. Size women’s 40 euro. 26.5 cms inner sole.", 9, "Boots", "Male", 30));
    clothingItems.add(new Footwear("Roxy Jandals", 30.00, "These pink Roxy Jandals are in great condition, size US 7, EUR 37", 8, "Sandals", "Female", 31));
    clothingItems.add(new Footwear("Clarks Dress Shoes", 60.00, "In excellent condition \n" +
            "Size uk 9, us 10, eur 43, cn 270", 9, "Dress Shoes", "Male", 32));
    clothingItems.add(new Footwear("RM Williams Chelsea boot", 45.00,
            "Lady Yearling/Chestnut/Rubber sole/ Womens US 7. \n" +
                    "\n" +
                    "For everyday wear!\n" +
                    "\n" +
                    "Owned for under 2 years. sole has alot of wear left, you can replace the heels individually really cheap. they were made in 2022\n" +
                    "\n" +
                    "Some minor cosmetic scratches and wear but with matching coloured polish and a conditioner once and a while will bring them back to new :)\n" +
                    "\n" +
                    "Selling because I do not wear anymore and needs a loving owner to carry on, originally over 600 new!  :)", 7, "Boots", "Female", 33));


  }


  public void addClothingItem(Clothing item) {
    clothingItems.add(item);
  }

  public void setClothingItems(List<Clothing> clothing){
    clothingItems = clothing;
  }

  public List<Clothing> getAllClothingItems() {
    // Return a copy of the list to avoid modifying the original data
    return new ArrayList<>(clothingItems);
  }

  // You might also have methods to filter by type (optional)

  public List<Clothing> updateCart() {
    return shoppingCart.updateCart(clothingItems);
  }

  public String getTotalPrice() {
    return shoppingCart.getTotalPrice(shoppingCart.updateCart(clothingItems));
  }

  public List<Clothing> getFavouritesList() {
    return favouritesList.updateFavouritesList(clothingItems);
  }

  public List<Clothing> mostViewedItems() {
    // Sort the clothing list by views in descending order
    List<Clothing> sortedClothes = new ArrayList<>(clothingItems);
    sortedClothes.sort(Comparator.comparingInt(Clothing::getViewCount).reversed());

    if (sortedClothes.size() <= 4) {
      return sortedClothes;
    } else {
      return sortedClothes.subList(0, 4);
    }
  }

  public List<Clothing> getFilteredClothes(String category) {
    return (clothingFilter.getFilteredClothes(clothingItems, category));
  }
}
