package application;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientIdImageView {

    private final List<Image> imageList = new ArrayList<>();
    private final Random random = new Random();

    public ClientIdImageView() {
        // Example: Add image resources from local files or resources
        // Replace with your actual paths
        imageList.add(new Image("file:resources/images/client1.png"));
        imageList.add(new Image("file:resources/images/client2.png"));
        imageList.add(new Image("file:resources/images/client3.png"));
        // Add more as needed
    }

    public Image getRandomImage() {
        if (imageList.isEmpty()) {
            return null; // Or throw an exception / return a default image
        }
        int index = random.nextInt(imageList.size());
        return imageList.get(index);
    }

    public void addImage(Image image) {
        imageList.add(image);
    }
}
