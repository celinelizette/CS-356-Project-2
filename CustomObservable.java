package miniTwitter;

public interface CustomObservable {
	//represents the Observable of the Observer Design Interface
	public void registerObserver(CustomObserver observer);

    public void removeObserver(CustomObserver observer);

    public void notifyObservers();
}
