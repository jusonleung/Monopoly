/**
 * Property is a subclass of Square that gets activated when stepped on by the player
 *
 * @author Juson Leung
 * @author Maisy Wong
 * @author Raphael Lee
 * @version 2.8
 */
public class Property extends Square{

    private Player owner;
    private int price;
    private int rent;

    /**
     * Constructs a <code>Property</code> object.
     * Only ownership information is modifiable by provided methods,
     * others are hardcoded in <code>Controller.initMap</code>
     *
     * @param name  the name of this property
     * @param price the money that required to purchase this property
     * @param rent  the money that other player need to pay when they step on it (if the property have a owner)
     */
    Property(String name, int price, int rent) {
        super(name);
        this.setPrice(price);
        this.setRent(rent);
    }

    /**
     * Overrides <code>stepOn</code> of <code>Square</code>
     * Removes <code>ownership</code> if the owner has been retired,
     * notifies the player that they have stepped on this particular property.
     * If unowned & player has enough money to purchase, gives them the option to;
     * if unowned & player does not have enough money to purchase, notifies the player that they can't;
     * if owned by themselves, notifies them nothing happened; and
     * if owned by someone else, makes them pay rent
     *
     * @param player the player that steps on this property square
     */
    @Override
    public void stepOn(Player player){
        if (getOwner() !=null && getOwner().isRetired())
            clearOwner();
        System.out.println("You have stepped on property-\""+ getName() +"\"");
        if (getOwner() == null){
            if (player.getMoney()>= getPrice()){
                System.out.println("\""+ getName() +"\" is not owned, and you can choose to buy it for $"+ getPrice() +". You currently have $"+player.getMoney());
                if (View.askYesOrNo("Please enter \"yes\" to purchase it or enter \"no\" to choose not to",player)){
                    setOwner(player);
                }
            }else {
                System.out.println("\""+ getName() +"\" is not owned, but you don't have enough money to buy it, so nothing happened");
            }
        }else if (getOwner() == player){
            System.out.println("\""+ getName() +"\" is yours, so nothing happened");
        }else {
            payRent(player);
        }
    }

    /**
     * Transfers rent from a player to the owner
     *
     * @param player the player that will pay rent
     */
    public void payRent(Player player){
        System.out.println("\""+ getName() +"\" is owned by "+ getOwner().getName()+" and you need to pay $"+ getRent() +" rent to them");
        player.setMoney(-getRent());
        getOwner().setMoney(getRent());
    }

    /** @return the player owner of this property */
    public Player getOwner(){
        return this.owner;
    }

    /**
     * Sets a player as the owner to this property
     *
     * @param owner the player that will be the owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
        if (this.owner != null){
            owner.setMoney(-this.getPrice());
            System.out.println(owner.getName()+" now own \""+ this.getName() +"\"");
        }
    }

    /**
     * Unregisters the owner from this property
     */
    public void clearOwner(){
        this.setOwner(null);
    }

    /** @return the price to purchase the Square for ownership */
    public int getPrice() {
        return price;
    }

    /** @param price to purchase the Square for ownership */
    public void setPrice(int price) {
        this.price = price;
    }

    /** @return price to pay the owner if stepped on */
    public int getRent() {
        return rent;
    }

    /** @param rent the price to pay if stepped on */
    public void setRent(int rent) {
        this.rent = rent;
    }
}
