import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.
    //QuadTree qt;
    int[] size = new int[]{2, 4, 8, 16, 32, 64, 128};

    public QTreeNode[][][] level = new QTreeNode[7][size[6]][size[6]];

    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
        level[0][0][0] = new QTreeNode("1", ROOT_ULLON, ROOT_ULLAT, (ROOT_LRLON + ROOT_ULLON) / 2, (ROOT_ULLAT + ROOT_LRLAT) / 2);
        level[0][0][1] = new QTreeNode("2", (ROOT_LRLON + ROOT_ULLON) / 2, ROOT_ULLAT, ROOT_LRLON, (ROOT_ULLAT + ROOT_LRLAT) / 2);
        level[0][1][0] = new QTreeNode("3", ROOT_ULLON, (ROOT_ULLAT + ROOT_LRLAT) / 2, (ROOT_ULLON + ROOT_LRLON) / 2, ROOT_LRLAT);
        level[0][1][1] = new QTreeNode("4", (ROOT_LRLON + ROOT_ULLON) / 2, (ROOT_ULLAT + ROOT_LRLAT) / 2, ROOT_LRLON, ROOT_LRLAT);
        String index = "";
        for(int i = 1; i < 7; i++){
            double wide = (ROOT_ULLON - ROOT_LRLON) / size[i];
            double height = (ROOT_ULLAT - ROOT_LRLAT) / size[i];
            for(int j = 0; j < size[i]; j++){
                for(int k = 0; k < size[i]; k++){
                    if(j % 2 == 0 && k % 2 == 0){
                        index = level[i - 1][j / 2][k / 2].index + "1";
                    }else if(j % 2 == 1 && k % 2 == 0){
                        index = level[i - 1][j / 2][k / 2].index + "3";
                    }else if(j % 2 == 0 && k % 2 == 1){
                        index = level[i - 1][j / 2][k / 2].index + "2";
                    }else if(j % 2 == 1 && k % 2 == 1){
                        index = level[i - 1][j / 2][k / 2].index + "4";
                    }
                    level[i][j][k] = new QTreeNode(index, ROOT_ULLON + wide * k, ROOT_ULLAT + height * j, ROOT_LRLON + height * (k + 1), ROOT_LRLAT + height * (k + 1));
                }
            }
        }
        /*
        qt = new QuadTree(new QTreeNode("root", ROOT_ULLON, ROOT_ULLAT, ROOT_LRLON, ROOT_LRLAT));
        qt.firstChild = construct("1", "", ROOT_ULLON, ROOT_ULLAT, (ROOT_LRLON + ROOT_ULLON) / 2, (ROOT_ULLAT + ROOT_LRLAT) / 2);
        qt.secondChild = construct("2", "", (ROOT_LRLON + ROOT_ULLON) / 2, ROOT_ULLAT, ROOT_LRLON, (ROOT_ULLAT + ROOT_LRLAT) / 2);
        qt.thirdChild= construct("3", "",  ROOT_ULLON, (ROOT_ULLAT + ROOT_LRLAT) / 2, (ROOT_ULLON + ROOT_LRLON) / 2, ROOT_LRLAT);
        qt.forthChild = construct("4", "",  (ROOT_LRLON + ROOT_ULLON) / 2, (ROOT_ULLAT + ROOT_LRLAT) / 2, ROOT_LRLON, ROOT_LRLAT);
        */
    }
    /*
    public QuadTree construct(String num,
                              String index,
                              Double img_ul_lon,
                              Double img_ul_lat,
                              Double img_lr_lon,
                              Double img_lr_lat){
        if(num.length() > 1){
            return null;
        }else{
            QuadTree res = new QuadTree(new QTreeNode(num + index, img_ul_lon, img_ul_lat, img_lr_lon, img_lr_lat));
            res.firstChild = construct(res.node.index, "1", img_ul_lon, img_ul_lat, (img_lr_lon + img_ul_lon) / 2, (img_ul_lat + img_lr_lat) / 2);
            res.secondChild = construct(res.node.index, "2", (img_lr_lon + img_ul_lon) / 2, img_ul_lat, img_lr_lon, (img_ul_lat + img_lr_lat) / 2);
            res.thirdChild = construct(res.node.index, "3", img_ul_lon, (img_ul_lat + img_lr_lat) / 2, (img_ul_lon + img_lr_lon) / 2, img_lr_lat);
            res.forthChild = construct(res.node.index, "4", (img_lr_lon + img_ul_lon) / 2, (img_ul_lat + img_lr_lat) / 2, img_lr_lon, img_lr_lat);
            return res;
        }
    }
    */
    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     * </p>
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified:
     * "render_grid"   -> String[][], the files to display
     * "raster_ul_lon" -> Number, the bounding upper left longitude of the rastered image <br>
     * "raster_ul_lat" -> Number, the bounding upper left latitude of the rastered image <br>
     * "raster_lr_lon" -> Number, the bounding lower right longitude of the rastered image <br>
     * "raster_lr_lat" -> Number, the bounding lower right latitude of the rastered image <br>
     * "depth"         -> Number, the 1-indexed quadtree depth of the nodes of the rastered image.
     *                    Can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" -> Boolean, whether the query was able to successfully complete. Don't
     *                    forget to set this to true! <br>
     * @see //REQUIRED_RASTER_REQUEST_PARAMS
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "+ "your browser.");
        double lonDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");//query box lonDPP.
        int depth = -1;
        double picLonDPP = (ROOT_LRLON - ROOT_ULLON) / 256;
        if(picLonDPP <= lonDPP){
            depth = -1;
            String[][] renderGrid = new String[][]{{"root"}};
            results.put("render_grid", renderGrid);
            results.put("raster_ul_lon", ROOT_ULLON);
            results.put("raster_ul_lat", ROOT_ULLAT);
            results.put("raster_lr_lon", ROOT_LRLON);
            results.put("raster_lr_lat", ROOT_LRLAT);
            results.put("depth", depth + 1);
            results.put("query_success", true);
            return results;
        }

        picLonDPP /= 2;
        for(int i = 0; i < 7; i++){
            depth = i;
            if(picLonDPP <= lonDPP){
                break;
            }
            picLonDPP /= 2;
        }
        System.out.println("LonDPP: "+ lonDPP);
        System.out.println("PicLonDPP: "+ picLonDPP);
        System.out.println("Depth: "+depth);
        double wide = (ROOT_LRLON - ROOT_ULLON) / size[depth];
        double height = (ROOT_ULLAT - ROOT_LRLAT) / size[depth];
        int wSize = (int)((params.get("lrlon") - params.get("ullon")) / wide);
        if(wSize < size[depth]){
            wSize += 1;
        }
        int hSize = (int)((params.get("ullat") - params.get("lrlat")) / height);
        if(hSize < size[depth]){
            hSize += 1;
        }
        System.out.println("wSize: "+wSize);
        System.out.println("hSize: "+hSize);
        String[][] renderGrid = new String[hSize][wSize];

        double firstLrLon = ROOT_ULLON + wide;
        double firstLrLat = ROOT_ULLAT - height;
        int uli = 0, ulj = 0;
        while(firstLrLon < params.get("ullon")){
            uli ++;
            firstLrLon += wide;
        }
        while(firstLrLat > params.get("ullat")){
            ulj ++;
            firstLrLat -= height;
        }

        System.out.println("uli: "+uli);
        System.out.println("ulj: "+ulj);
        for(int j = ulj; j < ulj + hSize; j++){
            for(int i = uli; i < uli + wSize; i++){
                renderGrid[j - ulj][i - uli] = "img/" + level[depth][j][i].index + ".png";
            }
        }
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", level[depth][uli][ulj].ul_lon);
        results.put("raster_ul_lat", level[depth][uli][ulj].ul_lat);
        results.put("raster_lr_lon", level[depth][uli + wSize - 1][ulj + hSize - 1].lr_lon);
        results.put("raster_lr_lat", level[depth][uli + wSize - 1][ulj + hSize - 1].lr_lat);
        results.put("depth", depth + 1);
        results.put("query_success", true);
        System.out.println(results);
        return results;
    }

    public static void main(String[] args){
        Rasterer rs = new Rasterer("...");
        QTreeNode[][] qt = rs.level[2];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                System.out.print(qt[i][j].index + " ");
            }
            System.out.println("");
        }
    }
}
