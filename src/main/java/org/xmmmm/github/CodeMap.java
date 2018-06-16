package org.xmmmm.github;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CodeMap {

    public void getMap(){




    }

    @Test
    public void test() throws IOException {
        BufferedImage image = ImageIO.read(new File("/Users/huangyitao/Documents/ppt.png"));
        System.out.println(image.getWidth());
        System.out.println(image.getHeight());
    }


}
