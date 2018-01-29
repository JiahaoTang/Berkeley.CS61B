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
    public static final Double[] LAYERS_LONG_DPP = new Double[]{0.000171661376953125,
                                                                0.0000858306884765625,
                                                                0.00004291534423828125,
                                                                0.000021457672119140625,
                                                                0.000010728836059570312,
                                                                0.000005364418029785156,
                                                                0.000002682209014892578};
    // Recommended: QuadTree instance variable. You'll need to make
    //              your own QuadTree since there is no built-in quadtree in Java.
    quadTree qt;

    /**
     * This class is built for quad tree;
     * Every quad tree has four children and a String type index to specifify
     * the name of image.
     */
    private class quadTree{
        String index;
        Double img_ul_lon;
        Double img_ul_lat;
        Double img_lr_lon;
        Double img_lr_lat;
        quadTree firstChild;
        quadTree secondChild;
        quadTree thirdChild;
        quadTree forthChild;

        quadTree(String index,
                 Double img_ul_lon,
                 Double img_ul_lat,
                 Double img_lr_lon,
                 Double img_lr_lat){
            this.index = index;
            this.img_ul_lon = img_ul_lon;
            this.img_ul_lat = img_ul_lat;
            this.img_lr_lon = img_lr_lon;
            this.img_lr_lat = img_lr_lat;
            this.firstChild = null;
            this.secondChild = null;
            this.thirdChild = null;
            this.forthChild = null;
        }
    }
    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public Rasterer(String imgRoot) {
        // YOUR CODE HERE
        qt = new quadTree("root", ROOT_ULLON, ROOT_ULLAT, ROOT_LRLON, ROOT_LRLAT);
        qt.firstChild = construct("1", "", ROOT_ULLON, ROOT_ULLAT, (ROOT_LRLON + ROOT_ULLON) / 2, (ROOT_ULLAT + ROOT_LRLAT) / 2);
        qt.secondChild = construct("2", "", (ROOT_LRLON + ROOT_ULLON) / 2, ROOT_ULLAT, ROOT_LRLON, (ROOT_ULLAT + ROOT_LRLAT) / 2);
        qt.thirdChild= construct("3", "",  ROOT_ULLON, (ROOT_ULLAT + ROOT_LRLAT) / 2, (ROOT_ULLON + ROOT_LRLON) / 2, ROOT_LRLAT);
        qt.forthChild = construct("4", "",  (ROOT_LRLON + ROOT_ULLON) / 2, (ROOT_ULLAT + ROOT_LRLAT) / 2, ROOT_LRLON, ROOT_LRLAT);
    }

    public quadTree construct(String num,
                              String index,
                              Double img_ul_lon,
                              Double img_ul_lat,
                              Double img_lr_lon,
                              Double img_lr_lat){
        if(num.length() == 7){
            return null;
        }else{
            quadTree res = new quadTree(num + index, img_ul_lon, img_ul_lat, img_lr_lon, img_lr_lat);
            res.firstChild = construct(res.index, "1", img_ul_lon, img_ul_lat, (img_lr_lon + img_ul_lon) / 2, (img_ul_lat + img_lr_lat) / 2);
            res.secondChild = construct(res.index, "2", (img_lr_lon + img_ul_lon) / 2, img_ul_lat, img_lr_lon, (img_ul_lat + img_lr_lat) / 2);
            res.thirdChild = construct(res.index, "3", img_ul_lon, (img_ul_lat + img_lr_lat) / 2, (img_ul_lon + img_lr_lon) / 2, img_lr_lat);
            res.forthChild = construct(res.index, "4", (img_lr_lon + img_ul_lon) / 2, (img_ul_lat + img_lr_lat) / 2, img_lr_lon, img_lr_lat);
            return res;
        }
    }

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
     * @see #REQUIRED_RASTER_REQUEST_PARAMS
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        double paraLongDPP = (params.get("lrlon") - params.get("ullon")) / params.get("w");

        //Find the proper depth which satisfied parameter longitude per pixel.
        int depth = 7;
        for(int i = 0; i < 7; i++){
            if(paraLongDPP >= LAYERS_LONG_DPP[i]) {
                depth = i + 1;
                break;
            }
        }
        results.put("depth", depth);

        int sideLength = 2 ^ depth;
        double lonPerPic = (ROOT_LRLON - ROOT_ULLON) / sideLength;
        double latPerPic = (ROOT_LRLAT - ROOT_ULLAT) / sideLength;

        double picLLon = ROOT_ULLON;
        int lonPicNum = 1;
        while(picLLon < params.get("ullon")){
            picLLon += lonPerPic;
            lonPicNum += 1;
        }
        int lonPicNumL = lonPicNum;             //The left picture index in longitude.
        while(picLLon < params.get("lrlon")){
            picLLon += lonPerPic;
            lonPicNum += 1;
        }
        int lonPicNumR = lonPicNum;             //The right picture index in longitude.

        double picLLat = ROOT_ULLAT;
        int latPicNum = 1;
        while(picLLat < params.get("ullat")){
            picLLat += latPerPic;
            latPicNum += 1;
        }
        int latPicNumU = latPicNum;             //The upper picture index in latitude.
        while(picLLat < params.get("lrlat")){
            picLLat += latPerPic;
            latPicNum += 1;
        }
        int latPicNumL = latPicNum;             //The lower picture index in latitude.
        StringBuilder ulIndex = new StringBuilder("");
        for(int i = 0; i < depth; i++){
            if(2 * lonPicNumL / (2 ^ (depth - i)) % 2 == 0)
                ulIndex.append("1");
            else
                ulIndex.append("2");
        }
        System.out.println(ulIndex);

        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                           + "your browser.");
        return results;
    }

}
