using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FollowTarget : MonoBehaviour
{
    public Transform target;
    Vector3 velocity = Vector3.zero;
    public float smoothTime = 0f;
    public bool YMax, YMin, XMax, XMin;
    public float YMaxVal, YMinVal, XMaxVal, XMinVal;
    // Start is called before the first frame update
    void Start()
    {
        YMax = YMin = XMax = XMin = false;
        YMaxVal = YMinVal = XMaxVal = XMinVal = 0;
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void FixedUpdate()
    {
        Vector3 targetPos = target.position;

        if (YMax && YMin) targetPos.y = Mathf.Clamp(target.position.y, YMinVal, YMaxVal);
        else if (YMin) targetPos.y = Mathf.Clamp(target.position.y, YMinVal, target.position.y);
        else if (YMax) targetPos.y = Mathf.Clamp(target.position.y, target.position.y, YMaxVal);

        if (XMax && XMin) targetPos.x = Mathf.Clamp(target.position.x, YMinVal, YMaxVal);
        else if (XMin) targetPos.x = Mathf.Clamp(target.position.x, YMinVal, target.position.x);
        else if (XMax) targetPos.x = Mathf.Clamp(target.position.x, target.position.x, XMaxVal);

        targetPos.z = transform.position.z;

        transform.position = Vector3.SmoothDamp(transform.position, targetPos, ref velocity, smoothTime);
    }
}
